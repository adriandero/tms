package at.snt.tms.rest;

import at.snt.tms.model.operator.Group;
import at.snt.tms.model.operator.Permission;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

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
                    EntityRevRepository tenderRevRepository) {
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

        this.revRepository = tenderRevRepository;

        // Adding demo data:
        Platform platform = this.platformRepository.save(new Platform("http://demo.at"));
        Tender tender = this.tenderRepository.save(new Tender("#1234", platform, "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal"))));

        tender.setDescription("Different Description");
        this.tenderRepository.save(tender);

        platform.setLink("auftrag.at");
        this.platformRepository.save(platform);

        System.out.println(revRepository.listRevisions(Tender.class, tender.getId()));
        System.out.println(revRepository.listRevisions(Platform.class, platform.getId()));

        Permission permission = this.permissionRepository.save(new Permission("admin"));
        Group g = this.groupRepository.save(new Group("tender_admin", permission));
        User u = this.userRepository.save(new User("user@snt.at", "Max", "Mustermann", new BCryptPasswordEncoder().encode("pass123"), g));
    }
}
