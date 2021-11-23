package at.snt.tms.processing;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultProducer;

import javax.mail.internet.MimeMultipart;

public class TenderProducer extends DefaultProducer {

    public TenderProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getIn();
        final String subject = message.getHeader("subject") + "";
        final MimeMultipart body = message.getBody(MimeMultipart.class); // Eine Menge online docs (einfach nachschauen)

        // TODO: Auf Basis von dem Code hier die derzeit relevanten Details aus der Mail ziehen (glaube das waren die Blöcke, maybe link?) und in die Konsole printen
        // Beachte dass es am besten wäre wenn du aus dem MimeMultipart html holst und das dann mit einer HTML library parsed
        // Lilia sollte eine gesucht haben, wenn nicht entweder ohne, oder selber eine finden.
        // Muss noch nicht zu perfekt sein, ist mal nur zum Herzeigen!

        System.out.println(subject + ":"+body.getBodyPart(0).getContent());
    }
}
