package at.snt.tms;

import org.apache.camel.builder.RouteBuilder;

public class FileRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:C:/Users/Dominik.Fluch/Desktop/input").to("file:C:/Users/Dominik.Fluch/Desktop/output");
    }

}