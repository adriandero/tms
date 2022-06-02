package at.snt.tms.mailing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class {@code InboundMailRoute.java}
 * <p>
 * Route responsible for forwarding mails to processors.
 *
 * @author Dominik Fluch
 */
@Component
public class InboundMail extends RouteBuilder {

    @Autowired
    private ReceiveMailProcessor receiveMailProcessor;

    @Value("${tms.mail.receive.url}")
    private String mailReceiveUrl;

    @Value("${tms.mail.enabled}")
    private boolean mailEnabled;

    @Autowired
    private ReceiveMailProcessor conversionProcessor;

    @Autowired
    private MailOAuthenticator customOAuth;

    @Override
    public void configure() throws Exception {
        if (!this.mailEnabled) return;

        this.getContext().getRegistry().bind("customOAuth", customOAuth);

        from(this.mailReceiveUrl)
                //.convertBodyTo(byte[].class)
                .routeId(this.getClass().getSimpleName())
                //try interpretation
                .doTry()
                .process(receiveMailProcessor)
                .doCatch(Exception.class)
                .log(LoggingLevel.ERROR, "Exception in " + this.getClass().getSimpleName() + ": ${header.logException}")
                .stop()
                .end();
    }

}
