package at.snt.tms.rest;

import at.snt.tms.model.database.operator.User;
import at.snt.tms.model.database.status.AssignedIntStatus;
import at.snt.tms.model.database.status.ExternalStatus;
import at.snt.tms.model.database.status.InternalStatus;
import at.snt.tms.model.database.tender.Assignment;
import at.snt.tms.model.database.tender.Company;
import at.snt.tms.model.database.tender.Platform;
import at.snt.tms.model.database.tender.Tender;
import at.snt.tms.repositories.operator.RoleRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Component
public class Database {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AssignedIntStatusRepository assignedIntStatusRepository;
    private final ExternalStatusRepository externalStatusRepository;
    private final InternalStatusRepository internalStatusRepository;
    private final AttachmentRepository attachmentRepository;
    private final CompanyRepository companyRepository;
    private final PlatformRepository platformRepository;
    private final TaskRepository taskRepository;
    private final TenderRepository tenderRepository;
    private final TenderUpdateRepository tenderUpdateRepository;
    private final AssignmentRepository assignmentRepository;

    public Database(RoleRepository roleRepository, UserRepository userRepository,
                    AssignedIntStatusRepository assignedIntStatusRepository,
                    ExternalStatusRepository externalStatusRepository,
                    InternalStatusRepository internalStatusRepository,
                    AttachmentRepository attachmentRepository, CompanyRepository companyRepository,
                    PlatformRepository platformRepository, TaskRepository taskRepository,
                    TenderRepository tenderRepository, TenderUpdateRepository tenderUpdateRepository, AssignmentRepository assignmentRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.assignedIntStatusRepository = assignedIntStatusRepository;
        this.externalStatusRepository = externalStatusRepository;
        this.internalStatusRepository = internalStatusRepository;
        this.attachmentRepository = attachmentRepository;
        this.companyRepository = companyRepository;
        this.platformRepository = platformRepository;
        this.taskRepository = taskRepository;
        this.tenderRepository = tenderRepository;
        this.tenderUpdateRepository = tenderUpdateRepository;
        this.assignmentRepository = assignmentRepository;


        // Adding demo data:
        final InternalStatus intStatus = new InternalStatus("internal");
        // terminates tender not included (from pending merge request)
//        intStatus.addTransition(this.internalStatusRepository.save(new InternalStatus("closed"))); wrong behaviour
        this.internalStatusRepository.save(intStatus);

        final Tender tender = this.tenderRepository.save(new Tender(1234L, "#1234", this.platformRepository.save(new Platform("http://demo.at")), "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), intStatus));
        this.tenderRepository.save(tender);

        final User user = new User("example@gmail.com", "secret");
        this.userRepository.save(user);

        final Tender tender1 = this.tenderRepository.save(new Tender(12345L, "#123", this.platformRepository.save(new Platform("http://demo.at")), "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal"))));

        final AssignedIntStatus assignedIntStatus = new AssignedIntStatus(999, intStatus, tender1, user, new Timestamp(1));
        Set<AssignedIntStatus> assInt = new HashSet<>();
        assInt.add(this.assignedIntStatusRepository.save(assignedIntStatus));
        tender1.setAssignedIntStatuses(assInt);
        this.tenderRepository.save(tender1);

        final Assignment assignment = new Assignment(tender1, user);
        this.assignmentRepository.save(assignment);
    }
//     TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

}
