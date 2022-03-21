package at.snt.tms.rest;

import at.snt.tms.model.operator.Role;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Platform;
import at.snt.tms.model.tender.Tender;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;


/**
 * Class {@code RestAPI.java}
 * <p>
 * Class preparing REST.
 *
 * @author Dominik Fluch & Maximilan Wolf
 */
@Component
public class RestAPI extends RouteBuilder {     // http://localhost:8080/

    private final TenderBean tenderBean;
    private final UserBean userBean;

    public RestAPI(TenderBean tenderBean, UserBean userBean) {
        this.tenderBean = tenderBean;
        this.userBean = userBean;
    }

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/tenders")
                .get()
                .to("bean:tenderBean?method=getTenders");
        rest("/tenders")
                .get("{id}")
                .to("bean:tenderBean?method=getTendersId(${header.id})");

        rest("/users")
                .get()
                .to("bean:userBean?method=getUsers");
        rest("/users")
                .get("{id}")
                .to("bean:userBean?method=getUsersId(${header.id})")
                .post().type(User.class)
                .route()
                .transform(simple("Created ${body}"));


        rest("/login")
                .post()
                .route()
                .choice()
                .when(simple("${body}.password").isEqualTo("admin"))
                .choice()
                .when(simple("${body}.name").isEqualTo("admin"))
                .transform(simple("Successful login"))
                .otherwise()
                .transform(simple("Invalid credentials"));
    }

}

@Component
class TenderBean {

    public Tender[] getTenders(){
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
        tender.setLink("www.aufrtag.at/exampleTender");
        tender.setPlatform(new Platform("Auftrag.at"));
        tender.setName("IT-Auftrag");
        tender.setLatestExStatus(new ExternalStatus("Update"));
        // tender.getAssignedIntStatuses().add(new AssignedIntStatus(new InternalStatus("Internal Status"), tender, new User(1234L, "test@test.com", new Role("role")), new Timestamp()));
        // tender.setLatestUpdate(new TenderUpdate());  // tender.updates is null...
        // tender.setLatestIntStatus(new InternalStatus("Interessant"));
        return tender;
    }
}

@Component
class UserBean {

    public User[] getUsers(){
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