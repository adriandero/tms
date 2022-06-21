package at.snt.tms.rest;

import at.snt.tms.model.operator.Group;
import at.snt.tms.model.operator.Permission;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.*;
import at.snt.tms.repositories.EntityRevisionsRepository;
import at.snt.tms.repositories.operator.GroupRepository;
import at.snt.tms.repositories.operator.PermissionRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

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

    private final EntityRevisionsRepository revRepository;

    @Autowired
    public Database(PermissionRepository permissionRepository, GroupRepository groupRepository,
                    UserRepository userRepository, AssignedIntStatusRepository assignedIntStatusRepository,
                    ExternalStatusRepository externalStatusRepository,
                    InternalStatusRepository internalStatusRepository,
                    AttachmentRepository attachmentRepository, CompanyRepository companyRepository,
                    PlatformRepository platformRepository, TenderRepository tenderRepository,
                    TenderUpdateRepository tenderUpdateRepository,AssignmentRepository assignmentRepository,
                    EntityRevisionsRepository tenderRevRepository) {
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

        // ------------------------------------------ Demo Data ------------------------------------------

        System.out.println("Adding demo data:");

        for (InternalStatus.Static status : InternalStatus.Static.values()) {
            this.internalStatusRepository.save(status.getInternalStatus());
        }

        // Adding demo data:
        final Platform platform = this.platformRepository.save(new Platform("http://demo.at"));
        final InternalStatus intStatus = this.internalStatusRepository.save(new InternalStatus("internal"));
        // terminates tender not included (from pending merge request)
//        intStatus.addTransition(this.internalStatusRepository.save(new InternalStatus("closed"))); wrong behaviour

        Permission permission = this.permissionRepository.save(new Permission("admin"));
        Group g = this.groupRepository.save(new Group("tender_admin", permission));
        User maxMustermann = this.userRepository.save(new User("user@snt.at", "Max", "Mustermann", new BCryptPasswordEncoder().encode("pass123"), g));

        final Tender tender = this.tenderRepository.save(new Tender("#1234", platform, "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), InternalStatus.Static.IRRELEVANT, 30));
        tender.addAssignedIntStatus(new AssignedIntStatus(intStatus, tender, maxMustermann, Timestamp.from(Instant.now())));
        tender.setUpdates(new HashSet<>(List.of(tenderUpdateRepository.save(new TenderUpdate(tender, this.externalStatusRepository.save(new ExternalStatus("external status0")), Timestamp.from(Instant.now()), "Hello hello hello", new HashSet<>())))));
        this.tenderRepository.save(tender);

        final User user = new User("example@gmail.com", new BCryptPasswordEncoder().encode("secret"));
        this.userRepository.save(user);

        Tender tender1 = this.tenderRepository.save(Tender.Builder.newInstance("#123", platform)
                .link("http://link.demo.at")
                .name("test")
                .company(this.companyRepository.save(new Company("Demo Company")))
                .description("Example demo fetched from database.")
                .latestExtStatus(this.externalStatusRepository.save(new ExternalStatus("external status2")))
                .prediction(InternalStatus.Static.INTERESTING, 67)
                .build());

        final AssignedIntStatus assignedIntStatus = this.assignedIntStatusRepository.save(new AssignedIntStatus(intStatus, tender1, null, new Timestamp(1)));
        tender1.addAssignedIntStatus(assignedIntStatus);
        this.tenderRepository.save(tender1);

        final Assignment assignment = new Assignment(tender1, maxMustermann);
        this.assignmentRepository.save(assignment);

        System.out.println(revRepository.listRevisions(Tender.class, tender.getId()));
        System.out.println(revRepository.listRevisions(Platform.class, platform.getId()));
    }

    public AssignedIntStatusRepository getAssignedIntStatusRepository() {
        return assignedIntStatusRepository;
    }

    public AssignmentRepository getAssignmentRepository() {
        return assignmentRepository;
    }

    public AttachmentRepository getAttachmentRepository() {
        return attachmentRepository;
    }

    public CompanyRepository getCompanyRepository() {
        return companyRepository;
    }

    public EntityRevisionsRepository getRevRepository() {
        return revRepository;
    }

    public ExternalStatusRepository getExternalStatusRepository() {
        return externalStatusRepository;
    }

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    public InternalStatusRepository getInternalStatusRepository() {
        return internalStatusRepository;
    }

    public PermissionRepository getPermissionRepository() {
        return permissionRepository;
    }

    public PlatformRepository getPlatformRepository() {
        return platformRepository;
    }

    public TenderRepository getTenderRepository() {
        return tenderRepository;
    }

    public TenderUpdateRepository getTenderUpdateRepository() {
        return tenderUpdateRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
