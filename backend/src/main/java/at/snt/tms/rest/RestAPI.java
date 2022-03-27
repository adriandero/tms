package at.snt.tms.rest;

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

        rest("/user")
                .get()
                .to("direct:user");
        // 2 options:
        // 1) findUsers is a method in the class Database
        from("direct:user")
                .bean(Database.class, "findUsers");
        // 2) directly reference findAll method in UserRepository
        /*from("direct:user")
                .routeId("user-api")
                .to("bean:userRepository?method=findAll");*/

        rest("/user/add")
                .post()
                .to("direct:user_add");
        from("direct:user_add")
                .bean(Database.class, "addUser");


        // TODO fix (consider replacing body with header)
        rest("/login")
                .post()
                .route()
                .choice()
                .when(simple("${body.password}").isEqualTo("admin"))
                .choice()
                .when(simple("${body.name}").isEqualTo("admin"))
                .transform(simple("Successful login"))
                .otherwise()
                .transform(simple("Invalid credentials"));


        /*restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/tenders")
                .get()
                .to("bean:tenderRepository?method=findAll");
        rest("/tenders")
                .get("{id}")
                .to("bean:tenderRepository?method=findById(${header.id})");

        rest("/users")
                .get()
                .to("bean:userRepository?method=findAll");
        rest("/users")
                .get("{id}")
                .to("bean:userRepository?method=findById(${header.id})")
                .post()
                .type(User.class)
                .route()
                .transform(simple("Created ${body}"));
         */


    }
}

/*
@Component
class TenderBean {

    public Tender[] getTenders() {
        Tender[] tenders = new Tender[3];
        tenders[0] = this.getTendersId(1);
        tenders[1] = this.getTendersId(2);
        tenders[2] = this.getTendersId(3);
        return tenders;
    }

    public Tender getTendersId(long id) {  // private set for id, AssignedIntStatus, ...
        Tender tender = new Tender();
        tender.setDescription("Software Programmierung");
        tender.setDocumentNumber("DKNR123");
        tender.setLink("www.auftrag.at/exampleTender");
        tender.setPlatform(new Platform("Auftrag.at"));
        tender.setName("IT-Auftrag");
        // tender.setLatestExStatus(new ExternalStatus("Update"));
        // tender.getAssignedIntStatuses().add(new AssignedIntStatus(new InternalStatus("Internal Status"), tender, new User(1234L, "test@test.com", new Role("role")), new Timestamp()));
        // tender.setLatestUpdate(new TenderUpdate());  // tender.updates is null...
        // tender.setLatestIntStatus(new InternalStatus("Interessant"));
        return tender;
    }
}

@Component
class UserBean {

    public User[] getUsers() {
        User[] users = new User[3];
        users[0] = this.getUsersId(0);
        users[1] = this.getUsersId(1);
        users[2] = this.getUsersId(2);
        return users;
    }

    public User getUsersId(long id) {
        return new User(id, "muster@mail.zb", new Role("admin"));
    }
}
*/
