package at.snt.tms.rest;

import org.apache.camel.builder.RouteBuilder;

/**
 * Class {@code RestRouteBuilder.java}
 * <p>
 * Class responsible for adding REST routes to camel.
 *
 * @author Dominik Fluch
 */
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty-http")
                .host("localhost").port(8080);

        rest().path("/my-api").get()
                .outType(String.class)
                .to("bean:helloBean");
    }

}
