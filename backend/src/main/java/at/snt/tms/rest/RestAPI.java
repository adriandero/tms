package at.snt.tms.rest;

import at.snt.tms.model.dtos.AccessRefreshTokenDto;
import at.snt.tms.model.dtos.request.UserLoginDto;
import at.snt.tms.model.dtos.request.UserSignUpDto;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Assignment;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.rest.services.*;
import org.apache.camel.Exchange;
import at.snt.tms.rest.services.tender.FilterConfiguration;
import at.snt.tms.rest.services.tender.TenderService;
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
                .post()
                .to("direct:allTenders")
                .consumes("application/json")
                .type(FilterConfiguration.class)
                .get("{id}")
                .to("direct:tenderId")
                .post("{id}/internalStatus")
                .consumes("application/json")
                .type(InternalStatus.class)
                .to("direct:updateInternal");

        from("direct:allTenders")
                .bean(TenderService.class, "findFiltered");
        from("direct:tenderId")
                .bean(TenderService.class, "findById(${header.id})");
        from("direct:updateInternal")
                .bean(TenderService.class, "updateInternal(${header.id}, ${body})");

        rest("/attachment")
                .get("{id}")
                .bindingMode(RestBindingMode.off)
                .to("direct:attachmentId");

        from("direct:attachmentId")
                .bean(AttachmentService.class, "findContent(${header.id})");

        rest("/users")
                .get()
                .to("direct:allUsers")

                .get("id/{id}")
                .to("direct:userId")

                .get("{mail}")
                .to("direct:findByUserMail")

                .post()
                .to("direct:addUser")
                .consumes("application/json")
                .type(UserSignUpDto.class)

                .delete("{id}")
                .to("direct:delUser");
        from("direct:allUsers")
                .bean(UserService.class, "findAll");
        from("direct:userId")
                .bean(UserService.class, "findById(${header.id})");
        from("direct:findByUserMail")
                .bean(UserService.class, "findByMail(${header.mail})");
        from("direct:addUser")
                .bean(UserService.class, "add");
        from("direct:delUser")
                .bean(UserService.class, "delete(${header.id})");

        rest("/groups")
                .get()
                .to("direct:allGroups");

        from("direct:allGroups")
                .bean(GroupService.class, "findAll");

        rest("/permissions")
                .get()
                .to("direct:allPermissions");

        from("direct:allPermissions")
                .bean(PermissionService.class, "findAll");

        rest("/internalStatus")
                .get()
                .to("direct:allIntStatus")
                .get("{id}")
                .to("direct:intStatusId")
                .post()
                .to("direct:addIntStatus")
                .delete("{id}")
                .to("direct:delIntStatus")
                .get("{id}/transitions")
                .to("direct:intStatusTransitions");

        from("direct:allIntStatus")
                .bean(IntStatusService.class, "findAll");
        from("direct:intStatusId")
                .bean(IntStatusService.class, "findById(${header.id})");
        from("direct:intStatusTransitions")
                .bean(IntStatusService.class, "transitions(${header.id})");
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
        from("direct:delCompany")
                .bean(CompanyService.class, "delete(${header.id})");

        rest("/assignments")
                .get()
                .to("direct:allAssignments")
                .get("{id}")
                .to("direct:assignmentId")
                .post()
                .consumes("application/json")
                .type(Assignment.class)
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

        rest("/assignments/tender")
                .get("{id}")
                .to("direct:assignmentsTenderId");
        from("direct:assignmentsTenderId")
                .bean(AssignmentService.class, "findAssignmentsByTenderId(${header.id})");



        // Authentication
        rest("/auth/login")
                .post().to("direct:login")
                .consumes("application/json")
                .type(UserLoginDto.class);
        from("direct:login")
                .bean(AuthService.class, "authenticateUser")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${body.statusCodeValue}"));

        rest("/auth/logout")
                .get().to("direct:logout");
        from("direct:logout")
                .bean(AuthService.class, "logoutUser")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${body.statusCodeValue}"));

        rest("/auth/refresh")
                .post().to("direct:refresh")
                .consumes("application/json")
                .type(AccessRefreshTokenDto.class);
        from("direct:refresh")
                .bean(AuthService.class, "refreshTokens")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${body.statusCodeValue}"));

        rest("/auth/validate")
                .post().to("direct:validate")
                .consumes("application/json")
                .type(AccessRefreshTokenDto.class);
        from("direct:validate")
                .bean(AuthService.class, "validateTokens")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${body.statusCodeValue}"));
    }
}
