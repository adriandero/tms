package at.snt.tms.rest.services;

import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.repositories.status.InternalStatusRepository;
import org.apache.camel.Header;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class IntStatusService extends GenericCrudRepoService<InternalStatus, String>{

    @Autowired
    public IntStatusService(InternalStatusRepository internalStates) {
        super(internalStates, InternalStatus.class);
    }

    public InternalStatus add(@Header(value = "label") String label) {
        return repository.save(new InternalStatus(label));
    }

    @Transactional
    public ResponseEntity<Set<InternalStatus>> transitions(@Header("id") String id) {
        var status = this.findById(id).getBody();

        Hibernate.initialize(status.getTransitions());

        return ResponseEntity.ok(status.getTransitions());
    }

}