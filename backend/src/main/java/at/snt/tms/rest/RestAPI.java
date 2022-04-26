package at.snt.tms.rest;

import at.snt.tms.rest.services.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;


/**
 * Class {@code RestAPI.java}
 * <p>
 * Class preparing REST.
 *
 * @author Dominik Fluch, Maximilian Wolf & Oliver Sommer
 */

@Component
public class RestAPI extends RouteBuilder {  // http://localhost:8080/
    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .apiProperty("api.title", "tms REST API")
                .apiProperty("api.version", "1.0")
                .port("8080")
                .bindingMode(RestBindingMode.json);

        rest("/tenders")
                .get()
                    .to("direct:allTenders")
                .get("{id}")
                    .to("direct:tenderId");
        from("direct:allTenders")
                .bean(TenderService.class, "findAll");
        from("direct:tenderId")
                .bean(TenderService.class, "findById(${header.id})");

        rest("/users")
                .get()
                    .to("direct:allUsers")
                .get("{id}")
                    .to("direct:userId")
                .post()
                    .to("direct:addUser")
                .delete("{id}")
                    .to("direct:delUser");
        from("direct:allUsers")
                .bean(UserService.class, "findAll");
        from("direct:userId")
                .bean(UserService.class, "findById(${header.id})");
        from("direct:addUser")
                .bean(UserService.class, "add");
        from("direct:delUser")
                .bean(UserService.class, "delete(${header.id})");

        rest("/internalStatus")
                .get()
                    .to("direct:allIntStatus")
                .get("{id}")
                    .to("direct:intStatusId")
                .post()
                    .to("direct:addIntStatus")
                .delete("{id}")
                    .to("direct:delIntStatus");
        from("direct:allIntStatus")
                .bean(IntStatusService.class, "findAll");
        from("direct:intStatusId")
                .bean(IntStatusService.class, "findById(${header.id})");
        from("direct:addIntStatus")
                .bean(IntStatusService.class, "add");
        from("direct:delIntStatus")
                .bean(IntStatusService.class, "delete(${header.id})");

        rest("/externalStatus")
                .get()
                    .to("direct:allExtStatus")
                .get("{id}")
                    .to("direct:extStatusId")
                .post()
                    .to("direct:addExtStatus")
                .delete("{id}")
                    .to("direct:delExtStatus");
        from("direct:allExtStatus")
                .bean(ExtStatusService.class, "findAll");
        from("direct:extStatusId")
                .bean(ExtStatusService.class, "findById(${header.id})");
        from("direct:addExtStatus")
                .bean(ExtStatusService.class, "add");
        from("direct:delExtStatus")
                .bean(ExtStatusService.class, "delete(${header.id})");

        rest("/companies")
                .get()
                    .to("direct:allCompanies")
                .get("{id}")
                    .to("direct:companyId")
                .post()
                    .to("direct:addCompany")
                .delete("{id}")
                    .to("direct:delCompany");
        from("direct:allCompanies")
                .bean(CompanyService.class, "findAll");
        from("direct:companyId")
                .bean(CompanyService.class, "findById(${header.id})");
        from("direct:addCompany")
                .bean(CompanyService.class, "add");
        from("direct:delCompany")
                .bean(CompanyService.class, "delete(${header.id})");

        rest("/assignments")
                .get()
                .to("direct:allAssignments")
                .get("{id}")
                .to("direct:assignmentId")
                .post()
                .to("direct:addAssignments")
                .delete("{id}")
                .to("direct:delAssignments");
        from("direct:allAssignments")
                .bean(AssignmentService.class, "findAll");
        from("direct:assignmentId")
                .bean(AssignmentService.class, "findById(${header.id})");
        from("direct:addAssignments")
                .bean(AssignmentService.class, "add");
        from("direct:delAssignments")
                .bean(AssignmentService.class, "delete(${header.id})");

        rest("assignments/tender")
                .get("{id}")
                .to("direct:assignmentsTenderId");
        from("direct:assignmentsTenderId")
                .bean(AssignmentService.class, "findAssignmentsByTenderId(${header.id})");


    }
}
