package at.snt.tms.rest;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.RoleRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.repositories.status.AssignedIntStatusRepository;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.*;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    public Database(RoleRepository roleRepository, UserRepository userRepository,
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

    public Iterable<User> findUsers() {
        return userRepository.findAll();
    }

    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

    public void addUser(@Header(value = "mail") String mail, @Header(value = "password") String password) {
        User u = new User(mail, password);
        System.out.println("Add user to database: " + u);
        userRepository.save(u);
    }
}
