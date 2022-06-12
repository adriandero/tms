package at.snt.tms.mailing;

import java.util.Map;

/**
 * Class {@code HandleableMail.java}
 * <p>
 * Class abstracting away all the irrelevant properties of mails.
 *
 * @author Dominik Fluch
 */
public class HandleableMail {

    private final Map<String, Object> headers;
    private final String body;
    private final Map<String, byte[]> attachments;

    protected HandleableMail(Map<String, Object> headers, String body, Map<String, byte[]> attachments) {
        this.headers = headers;
        this.body = body;
        this.attachments = attachments;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

}
