package at.snt.tms;

import at.snt.tms.mailing.MailRouteBuilder;
import at.snt.tms.processing.MailProcessingEndpoint;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class TenderManager {

    public static void main(String[] args) throws Exception {
        final CamelContext context = new DefaultCamelContext();
        context.addEndpoint("mail-processor", new MailProcessingEndpoint(context));

        context.addRoutes(new MailRouteBuilder());
        context.start();

        Thread.sleep(1000L * 60L * 3L);
        System.out.println("Shutting down (see TenderManager class)");
        context.stop();
    }

}
