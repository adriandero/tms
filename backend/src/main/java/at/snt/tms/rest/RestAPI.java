package at.snt.tms.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Class {@code null.java}
 * <p>
 * TODO: Add description
 *
 * @author Dominik Fluch
 */
@Component
public class RestAPI extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/test")
                .get("/").to("direct:hello");

        from("direct:hello").transform().constant("my great api response from a get lol");
    }

}
