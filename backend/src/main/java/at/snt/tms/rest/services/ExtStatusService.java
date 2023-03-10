package at.snt.tms.rest.services;

import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtStatusService extends GenericCrudRepoService<ExternalStatus, Long>{

    @Autowired
    public ExtStatusService(ExternalStatusRepository externalStates) {
        super(externalStates, ExternalStatus.class);
    }

    public void add(@Header(value = "label") String label) {
        repository.save(new ExternalStatus(label));
    }
}