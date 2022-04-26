package at.snt.tms.rest;

import at.snt.tms.model.operator.Role;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Assignment;
import at.snt.tms.model.tender.Company;
import at.snt.tms.model.tender.Platform;
import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.operator.RoleRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.springframework.stereotype.Component;

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
        final Tender tender = new Tender(1234L, "#1234", this.platformRepository.save(new Platform("http://demo.at")), "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal")));
        this.tenderRepository.save(tender);
        final User user = new User("example@gmail.com", "secret");
        final Assignment assignment = new Assignment(
                this.tenderRepository.save(new Tender(12345L, "#1234", this.platformRepository.save(new Platform("http://demo.at")), "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal"))))
                , this.userRepository.save(user));
        this.assignmentRepository.save(assignment);
    }
    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

}
