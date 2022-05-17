package at.snt.tms.rest;

import at.snt.tms.model.operator.Group;
import at.snt.tms.model.operator.Permission;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Assignment;
import at.snt.tms.model.tender.Company;
import at.snt.tms.model.tender.Platform;
import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.EntityRevRepository;
import at.snt.tms.repositories.operator.PermissionRepository;
import at.snt.tms.repositories.operator.GroupRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class Database {

    private final PermissionRepository permissionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AssignedIntStatusRepository assignedIntStatusRepository;
    private final ExternalStatusRepository externalStatusRepository;
    private final InternalStatusRepository internalStatusRepository;
    private final AttachmentRepository attachmentRepository;
    private final CompanyRepository companyRepository;
    private final PlatformRepository platformRepository;
    private final TenderRepository tenderRepository;
    private final TenderUpdateRepository tenderUpdateRepository;
    private final AssignmentRepository assignmentRepository;

    private final EntityRevRepository revRepository;

    @Autowired
    public Database(PermissionRepository permissionRepository, GroupRepository groupRepository,
                    UserRepository userRepository,
                    AssignedIntStatusRepository assignedIntStatusRepository,
                    ExternalStatusRepository externalStatusRepository,
                    InternalStatusRepository internalStatusRepository,
                    AttachmentRepository attachmentRepository, CompanyRepository companyRepository,
                    PlatformRepository platformRepository, TenderRepository tenderRepository,
                    TenderUpdateRepository tenderUpdateRepository,
                    EntityRevRepository tenderRevRepository, AssignmentRepository assignmentRepository) {
        this.permissionRepository = permissionRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.assignedIntStatusRepository = assignedIntStatusRepository;
        this.externalStatusRepository = externalStatusRepository;
        this.internalStatusRepository = internalStatusRepository;
        this.attachmentRepository = attachmentRepository;
        this.companyRepository = companyRepository;
        this.platformRepository = platformRepository;
        this.tenderRepository = tenderRepository;
        this.tenderUpdateRepository = tenderUpdateRepository;
        this.assignmentRepository = assignmentRepository;
        this.revRepository = tenderRevRepository;



        // Adding demo data:
        final Platform platform = this.platformRepository.save(new Platform("http://demo.at"));
        final InternalStatus intStatus = new InternalStatus("internal");
        // terminates tender not included (from pending merge request)
//        intStatus.addTransition(this.internalStatusRepository.save(new InternalStatus("closed"))); wrong behaviour
        this.internalStatusRepository.save(intStatus);

        final Tender tender = this.tenderRepository.save(new Tender(1234L, "#1234", platform, "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), intStatus));
        this.tenderRepository.save(tender);

        final User user = new User("example@gmail.com", new BCryptPasswordEncoder().encode("secret"));
        this.userRepository.save(user);

        final Tender tender1 = this.tenderRepository.save(new Tender(12345L, "#123", platform, "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal"))));

        final AssignedIntStatus assignedIntStatus = new AssignedIntStatus(999, intStatus, tender1, user, new Timestamp(1));
        Set<AssignedIntStatus> assInt = new HashSet<>();
        assInt.add(this.assignedIntStatusRepository.save(assignedIntStatus));
        tender1.setAssignedIntStatuses(assInt);
        this.tenderRepository.save(tender1);

        final Assignment assignment = new Assignment(tender1, user);
        this.assignmentRepository.save(assignment);
//     TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

        System.out.println(revRepository.listRevisions(Tender.class, tender.getId()));
        System.out.println(revRepository.listRevisions(Platform.class, platform.getId()));

        Permission permission = this.permissionRepository.save(new Permission("admin"));
        Group g = this.groupRepository.save(new Group("tender_admin", permission));
        this.userRepository.save(new User("user@snt.at", "Max", "Mustermann", new BCryptPasswordEncoder().encode("pass123"), g));
    }
}
