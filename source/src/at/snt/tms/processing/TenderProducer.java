package at.snt.tms.processing;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultProducer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class CURRENTLY tailored to AuftragAT (subject to change!)
 */
public class TenderProducer extends DefaultProducer {

    private static final Pattern URL_REGEX = Pattern.compile("http(s)?:\\/\\/[0-9a-zA-Z_\\.~\\-\\!\\*\\%\\'\\(\\)\\;\\:\\@\\&\\=\\+\\$\\,\\/\\?\\#\\[\\]]+"); // Does not need to be perfect and match every valid URL, but should match all auftrag.at ones.
    private static final String[] CATEGORY_NAMES = {"Beschreibung","Bekanntmachungsart","Veröffentlicht am","Dokumentnummer"};
    private static final String[] CATEGORY_IDs = {"ctl00_Content_usrAnonymEtender_TPropDescr", "ctl00_Content_usrAnonymEtender_TPropFormType", "ctl00_Content_usrAnonymEtender_TPropPublishDate", "ctl00_Content_usrAnonymEtender_TPropNoticeNumber"};

    private final HashMap<String, HashMap<String, String>> tenders = new HashMap<>();  // <Link<Category,Info>>

    public TenderProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getIn();
        final MimeMultipart body = message.getBody(MimeMultipart.class);

        if(!(message.getHeader("subject") + "").contains("Treffer")) return;

        final String[] links = findLinks(body);

        TenderProducer.showTenders(TenderProducer.fetchInformationFromTenderLink(links));
    }

    private static void showTenders(Map<String, Map<String, String>> information) {
        information.values().forEach(tender -> tender.forEach((category, info) ->
                System.out.printf("%n%s : %s", category, info)));
    }

    /**
     * Fetches all the information available when following the given tender links.
     * @param links
     * @throws IOException
     */
    private static Map<String, Map<String, String>> fetchInformationFromTenderLink(String... links) throws IOException {
        final Map<String, Map<String, String>> information = new HashMap<>();

        for (String link : links){
            final Map<String, String> details = new HashMap<String, String>();
            final Document document = Jsoup.connect(link).get();

            details.put("Titel", document.getElementsByClass("boxHeader").text()); // .substring(7)

            for (int i = 0; i < CATEGORY_NAMES.length; i++) {
                final Element element = document.getElementById(String.format("%s", CATEGORY_IDs[i]));

                if (element != null) {
                    details.put(CATEGORY_NAMES[i], element.text());
                }
            }
            information.put(link, details);
        }

        return information;
    }

    /**
     * Finds all the links from the given body.
     * @param body
     * @return all the links found.
     */
    private static String[] findLinks(MimeMultipart body) throws IOException, MessagingException {
        final List<String> links = new ArrayList<>();
        String content = (body.getBodyPart(0).getContent() + "");

        content = content.substring(content.indexOf("============================"), content.lastIndexOf("============================"));

        final Matcher matcher = TenderProducer.URL_REGEX.matcher(content);

        while(matcher.find()) links.add(matcher.group());

        return links.toArray(new String[0]);
    }

}
