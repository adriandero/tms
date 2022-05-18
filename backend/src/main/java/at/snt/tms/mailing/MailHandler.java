package at.snt.tms.mailing;

import org.apache.camel.Message;

/**
 * Class {@code MailHandler.java}
 * <p>
 * Class representing a single handler.
 *
 * @author Dominik Fluch
 */
public interface MailHandler {

    /**
     * Method that gets called when the handler is loaded.
     */
    public void onLoad();

    /**
     * Method that is called when the handler is unloaded.
     */
    public void onUnload();

    /**
     * @param message
     * @return true if the given message could be processed by the handler.
     */
    public boolean handle(Message message);

}
