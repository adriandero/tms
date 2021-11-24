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
import java.util.HashMap;


public class TenderProducer extends DefaultProducer {

    private final HashMap<String, HashMap<String, String>> tenders = new HashMap<>();  // <Link<Category,Info>>
    private final String[] CATEGORY_NAMES = {"Beschreibung","Bekanntmachungsart","Veröffentlicht am","Dokumentnummer"};
    private final String[] CATEGORY_IDs =   {"ctl00_Content_usrAnonymEtender_TPropDescr",
                                             "ctl00_Content_usrAnonymEtender_TPropFormType",
                                             "ctl00_Content_usrAnonymEtender_TPropPublishDate",
                                             "ctl00_Content_usrAnonymEtender_TPropNoticeNumber"};

    public TenderProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getIn();
        final MimeMultipart body = message.getBody(MimeMultipart.class);

        createTendersFromLinksInMail(body);
        getTenderInformationFromLinks();
        showTenders();
//        final String subject = message.getHeader("subject") + "";
//        System.out.println(subject + ":"+body.getBodyPart(0).getContent());
    }

    private void showTenders(){
        tenders.values().forEach(tender -> tender.forEach((category, info) ->
                System.out.printf("%n%s : %s", category, info)));
    }

    private void getTenderInformationFromLinks() throws IOException {
        for (String link : tenders.keySet()){
            Document doc = Jsoup.connect(link).get();
//            System.out.println(doc);
            tenders.get(link).put("Titel", doc.getElementsByClass("boxHeader").text()); // .substring(7)
            for (int i = 0; i < CATEGORY_NAMES.length; i++) {
                Element element = doc.getElementById(String.format("%s", CATEGORY_IDs[i]));
                if (element != null) {
                    tenders.get(link).put(CATEGORY_NAMES[i], element.text());
                }
            }
        }
    }

    private void createTendersFromLinksInMail(MimeMultipart body) throws IOException, MessagingException {
        String mailContent = body.getBodyPart(0).getContent().toString();
        String[] lines = mailContent.split(System.lineSeparator());
        for (String line : lines){
            if (line.contains("<") && line.contains("emea01")){  // safe, could change? "https://emea01. ...
                String link = line.substring(line.indexOf("<")+1, line.indexOf(">"));
                tenders.put(link, new HashMap<>());
            }
        }
    }
}
