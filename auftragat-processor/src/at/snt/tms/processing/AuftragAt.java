package at.snt.tms.processing;


/**
 * Class {@code AuftragAt.java}
 * <p>
 * Auftrag.at mail processor.
 *
 * @author Dominik Fluch
 */
public class AuftragAt implements at.snt.tms.mailing.MailHandler {

    @Override
    public void onLoad() {
        System.out.println("Hello world from extension!!");
    }

    @Override
    public void onUnload() {

    }


}
