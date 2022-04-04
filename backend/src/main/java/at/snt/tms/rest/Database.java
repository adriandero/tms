package at.snt.tms.rest;

import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
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
    private final TenderRepository tenderRepository;
    private final TenderUpdateRepository tenderUpdateRepository;

    public Database(RoleRepository roleRepository, UserRepository userRepository,
                    AssignedIntStatusRepository assignedIntStatusRepository,
                    ExternalStatusRepository externalStatusRepository,
                    InternalStatusRepository internalStatusRepository,
                    AttachmentRepository attachmentRepository, CompanyRepository companyRepository,
                    PlatformRepository platformRepository, TenderRepository tenderRepository,
                    TenderUpdateRepository tenderUpdateRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.assignedIntStatusRepository = assignedIntStatusRepository;
        this.externalStatusRepository = externalStatusRepository;
        this.internalStatusRepository = internalStatusRepository;
        this.attachmentRepository = attachmentRepository;
        this.companyRepository = companyRepository;
        this.platformRepository = platformRepository;
        this.tenderRepository = tenderRepository;
        this.tenderUpdateRepository = tenderUpdateRepository;

        // Adding demo data:
        final Tender tender = new Tender(1234L, "#1234", this.platformRepository.save(new Platform("http://demo.at")), "http://link.demo.at", "test", this.companyRepository.save(new Company("Demo Company")), "Example demo fetched from database.", this.externalStatusRepository.save(new ExternalStatus("external status")), this.internalStatusRepository.save(new InternalStatus("internal")));

        this.tenderRepository.save(tender);
    }
    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions Example 108

}
