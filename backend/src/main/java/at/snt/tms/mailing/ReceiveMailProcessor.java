package at.snt.tms.mailing;

import at.snt.tms.rest.Database;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.internet.MimeUtility;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveMailProcessor.class);

    @Autowired
    private ExtensionsManager extensionsManager;

    @Override
    public void process(Exchange exchange) throws Exception {
        final Map<String, byte[]> attachments = new HashMap<>();

        try {
            AttachmentMessage attachmentMessage = exchange.getMessage(AttachmentMessage.class);
            Map<String, DataHandler> rawAttachments = attachmentMessage.getAttachments();

            if(rawAttachments != null) {
                for (String name : rawAttachments.keySet()) {
                    DataHandler dh = rawAttachments.get(name);

                    String filename = dh.getName();

                    byte[] data = exchange.getContext().getTypeConverter()
                            .convertTo(byte[].class, dh.getInputStream());
                    attachments.put(filename, data);
                }
            }
        } catch (Exception e) {}

        final HandleableMail mail = new HandleableMail(exchange.getIn().getHeaders(), MimeUtility.decodeText(MimeUtility.unfold(exchange.getIn().getBody(String.class))), attachments);

        for(MailHandler[] handlers : this.extensionsManager.getLoaded().values()) {
            for(MailHandler handler : handlers) {
                try {
                    if(handler.handle(this.extensionsManager, mail)) {
                        return;
                    }
                } catch(Throwable exception) {
                    exception.printStackTrace();
                }
            }
        }
        LOGGER.warn("Couldn't handle mail from \"" + mail.getHeaders().get("from") + "\".");
    }

}
