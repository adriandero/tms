package at.snt.tms.rest;

import at.snt.tms.repositories.operator.RoleRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.springframework.stereotype.Component;

@Component
public class Database {
    private final RoleRepository roleRepository;
    private final at.snt.tms.repositories.operator.UserRepository userRepository;
    private final AssignedIntStatusRepository assignedIntStatusRepository;
    private final ExternalStatusRepository externalStatusRepository;
    private final InternalStatusRepository internalStatusRepository;
    private final AttachmentRepository attachmentRepository;
    private final CompanyRepository companyRepository;
    private final PlatformRepository platformRepository;
    private final TaskRepository taskRepository;
    private final TenderRepository tenderRepository;
    private final TenderUpdateRepository tenderUpdateRepository;

    public Database(RoleRepository roleRepository, at.snt.tms.repositories.operator.UserRepository userRepository,
                    AssignedIntStatusRepository assignedIntStatusRepository,
                    ExternalStatusRepository externalStatusRepository,
                    InternalStatusRepository internalStatusRepository,
                    AttachmentRepository attachmentRepository, CompanyRepository companyRepository,
                    PlatformRepository platformRepository, TaskRepository taskRepository,
                    TenderRepository tenderRepository, TenderUpdateRepository tenderUpdateRepository) {
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
    }
    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

}
