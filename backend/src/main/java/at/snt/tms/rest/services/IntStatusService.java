package at.snt.tms.rest.services;

import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.repositories.status.InternalStatusRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntStatusService extends GenericCrudRepoService<InternalStatus, String>{

    @Autowired
    public IntStatusService(InternalStatusRepository internalStates) {
        super(internalStates, InternalStatus.class);
    }

    public InternalStatus add(@Header(value = "label") String label) {
        InternalStatus intS = new InternalStatus(label);
        System.out.println("Add internal status to database: " + intS);
        return repository.save(intS);
    }
}