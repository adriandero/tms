package at.snt.tms;

import at.snt.tms.rest.HelloBean;
import at.snt.tms.rest.RestRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Class {@code TenderManager.java}
 * <p>
 * Main class of project used to bootstrap all components.
 *
 * @author Dominik Fluch
 */
public class TenderManager {

    /**
     * Runs the tms backend.
     * @param args
     */
    public static void main(String[] args) throws Exception { // TODO: Handle this exception with some nice logging; This is not very clean
        try (CamelContext camel = new DefaultCamelContext()) {
            camel.addRoutes(new RestRouteBuilder());
            camel.getRegistry().bind("helloBean", new HelloBean()); // Dirty; Pretty sure there is a better way but for demonstration only

            camel.start();

            Thread.sleep(100_000_000);
        }
    }

}
