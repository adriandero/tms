package at.snt.tms;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

    public static void main(String[] args) throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        FileRouteBuilder fileRouteBuilder = new FileRouteBuilder();

        try {
            ctx.addRoutes(fileRouteBuilder);
            ctx.start();
            Thread.sleep(5 * 60 * 1000);
            ctx.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
