package at.snt.tms.mailing;

import at.snt.tms.rest.Database;
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
     * @param extensionsManager
     * Method that gets called when the handler is loaded.
     */
    public void onLoad(ExtensionsManager extensionsManager);

    /**
     * @param extensionsManager
     * Method that is called when the handler is unloaded.
     */
    public void onUnload(ExtensionsManager extensionsManager);

    /**
     * @param extensionsManager
     * @param mail
     * @return true if the given message could be processed by the handler.
     */
    public boolean handle(ExtensionsManager extensionsManager, HandleableMail mail);

}
