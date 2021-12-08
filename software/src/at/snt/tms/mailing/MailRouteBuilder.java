package at.snt.tms.mailing;

import at.snt.tms.processing.MailProcessingEndpoint;
import at.snt.tms.processing.TenderProducer;
import org.apache.camel.builder.RouteBuilder;

public class MailRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("imaps://imap.gmail.com:993?username=tcool1749@gmail.com&password=5xsNSWWapQXVpg9"
                + "&delete=false&unseen=false").to("mail-processor");
    }
}
