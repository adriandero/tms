package at.snt.tms.processing;



import at.snt.tms.mailing.ExtensionsManager;
import at.snt.tms.mailing.HandleableMail;
import at.snt.tms.mailing.MailHandler;
import at.snt.tms.model.classifier.ClassifierPredictionDetails;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.*;
import at.snt.tms.rest.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.cert.Extension;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuftragAt implements MailHandler {

    private static final HashSet<String> POSSIBLE_STATES = new HashSet<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AuftragAt.class);

    private static final String BEGIN_DELIMITER = "=========================Ihre potentiellen Aufträge============================";
    private static final String END_DELIMITER = "=========================HINWEISE zu den Suchergebnissen============================";

    private static final Pattern TITLE_REGEX = Pattern.compile("(?s)<b>(.*?)</b>");
    private static final Pattern BEKANNTMACHUNGSART_REGEX = Pattern.compile("(?s)Bekanntmachungsart: (.*?)<br>");
    private static final Pattern BESCHREIBUNG_REGEX = Pattern.compile("(?s)Beschreibung: (.*?)<br>");
    private static final Pattern AUFTRAGGEBER_REGEX = Pattern.compile("(?s)Auftraggeber: (.*?)<br>");
    private static final Pattern LINK_REGEX = Pattern.compile("(?s)<a href=\"(.*?)\">Details</a>");

    // private final ExecutorService queue;

    private Platform auftrag;

    @Override
    public void onLoad(ExtensionsManager extensionsManager) {
        this.initPlatform(extensionsManager);
    }

    private void initPlatform(ExtensionsManager extensionsManager) {
        // We won't have too many platforms so fetching all of them should be fine.
        for(Platform platform : extensionsManager.getDatabase().getPlatformRepository().findAll()) {
            if(platform.getLink().equals("https://auftrag.at")) {
                this.auftrag = platform;
                return;
            }
        }

        this.auftrag = extensionsManager.getDatabase().getPlatformRepository().save(new Platform("https://auftrag.at"));
    }

    @Override
    public void onUnload(ExtensionsManager extensionsManager) {}

    @Override
    public boolean handle(ExtensionsManager extensionsManager, HandleableMail handleableMail) {
        final String senders = ((String) handleableMail.getHeaders().get("from")).toLowerCase();

        if(senders.contains("office@auftrag.at") || senders.contains("dominik.fluch@snt.at")) {
            String body = handleableMail.getBody().substring(handleableMail.getBody().indexOf(AuftragAt.BEGIN_DELIMITER) + AuftragAt.BEGIN_DELIMITER.length(), handleableMail.getBody().indexOf(AuftragAt.END_DELIMITER));

            try {
                // Auftrag.AT uses iso-8859-1
                body = new String(body.getBytes(), "iso-8859-1");
            } catch(UnsupportedEncodingException exception) {
                LOGGER.error("Failed to use auftrag.at encoding: " + exception.getMessage(), exception);
            }

            final Matcher titleMatcher = AuftragAt.TITLE_REGEX.matcher(body);
            final Matcher bekanntmachungArtMatcher = AuftragAt.BEKANNTMACHUNGSART_REGEX.matcher(body);
            final Matcher beschreibungMatcher = AuftragAt.BESCHREIBUNG_REGEX.matcher(body);
            final Matcher auftraggeberMatcher = AuftragAt.AUFTRAGGEBER_REGEX.matcher(body);
            final Matcher linkMatcher = AuftragAt.LINK_REGEX.matcher(body);

            while(titleMatcher.find()) {
                final String title = titleMatcher.group(1);
                //System.out.println(title);
                if(!(bekanntmachungArtMatcher.find() && beschreibungMatcher.find() && auftraggeberMatcher.find() && linkMatcher.find())) {
                    System.err.println("Failed to parse incoming tender."); // TODO: Use logger
                    break;
                }

                // TODO: Consider switching to fetch-only for possible more recent data.
                final String bekanntmachungsArt = bekanntmachungArtMatcher.group(1);
                final String beschreibung = beschreibungMatcher.group(1);
                final String auftraggeber = auftraggeberMatcher.group(1);
                final String link = linkMatcher.group(1);
                final Company company = extensionsManager.getDatabase().getCompanyRepository().findByName(auftraggeber);
                String documentNumber = null;
                POSSIBLE_STATES.add(bekanntmachungsArt);

                try {
                    documentNumber = fetchDocumentNumber(link);
                } catch(IOException exception) {
                    LOGGER.error("Failed to download document number for tender \"" + title + "\". Discarding it." , exception);
                    continue;
                }

                Tender existing = extensionsManager.getDatabase().getTenderRepository().findByDocumentNumberAndPlatform(documentNumber, this.auftrag);

                ExternalStatus externalStatus = extensionsManager.getDatabase().getExternalStatusRepository().findByLabel(bekanntmachungsArt);

                if(externalStatus == null) externalStatus = extensionsManager.getDatabase().getExternalStatusRepository().save(new ExternalStatus(bekanntmachungsArt));

                if(company == null || existing == null) {
                    existing = this.createTender(extensionsManager, documentNumber, link, title, company != null ? company : extensionsManager.getDatabase().getCompanyRepository().save(new Company(auftraggeber)), beschreibung, externalStatus);
                } else {
                    existing.getLatestExtStatus().addTransition(externalStatus);
                }

                final TenderUpdate update = extensionsManager.getDatabase().getTenderUpdateRepository().save(new TenderUpdate(existing, externalStatus, Timestamp.from(Instant.now()), beschreibung, new HashSet<>()));


                for(String name : handleableMail.getAttachments().keySet()) {
                    final byte[] content = handleableMail.getAttachments().get(name);

                    try {
                        update.getAttachedFiles().add(extensionsManager.getDatabase().getAttachmentRepository().save(new Attachment(name, new SerialBlob(content), update)));
                    } catch(SQLException exception) {
                        LOGGER.error("Failed to store attachment \"" + name + "\".", exception);
                    }
                }

                existing.getUpdates().add(update);
                existing.setLatestUpdate(update);
            }

            return true;
        }

        return false;
    }

    private Tender createTender(ExtensionsManager extensionsManager, String documentNumber, String link, String title, Company company, String beschreibung, ExternalStatus externalStatus) {
        final Tender tender = new Tender(-1L, documentNumber, this.auftrag, link, title, company, beschreibung, externalStatus, InternalStatus.Static.UNCHECKED.getInternalStatus(), null, -1);

        try {
            final ClassifierPredictionDetails prediction = extensionsManager.getClassifierBridge().predict(tender).get();

            tender.setPredictedIntStatus(prediction.getLabel());
            tender.setPredictionAccuracy(prediction.getConfidence());
        }catch(IOException | URISyntaxException | InterruptedException | ExecutionException exception) {
            LOGGER.error("Failed to predict for tender with name \"" + tender.getName() + "\".", exception);
        }

        return extensionsManager.getDatabase().getTenderRepository().save(tender);
    }

    /**
     * Fetches the document number of the {@link Tender} using the given link.
     * @return the document number.
     */
    private static String fetchDocumentNumber(String link) throws IOException {
        final Document document = Jsoup.connect(link).get();

        return document.getElementById("ctl00_Content_usrAnonymEtender_TPropNoticeNumber").text();
    }
}
