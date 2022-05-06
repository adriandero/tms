package at.snt.tms.mailing;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code ReceiveMailProcessor.java}
 * <p>
 * First layer of mail processing, which does the following:
 * - Take out message
 * - Determine message classifier to be used
 * - Use classifier to select data
 * - Store data in database
 *
 * @author Dominik Fluch
 */
@Component
public class ReceiveMailProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // demo code
        final Map<String, byte[]> attachments = new HashMap<>();

        try {
            AttachmentMessage attachmentMessage = exchange.getMessage(AttachmentMessage.class);
            Map<String, DataHandler> rawAttachments = attachmentMessage.getAttachments();
            if (rawAttachments.size() > 0) {
                for (String name : rawAttachments.keySet()) {
                    DataHandler dh = rawAttachments.get(name);

                    String filename = dh.getName();

                    byte[] data = exchange.getContext().getTypeConverter()
                            .convertTo(byte[].class, dh.getInputStream());
                    attachments.put(filename, data);
                }
            }
        } catch (Exception e) {}

        Message in = exchange.getIn();
        String xml = in.getBody(String.class) + "";
        String senders = (String) in.getHeader("from");

        System.out.println(xml + " --- " + senders);
    }

}
