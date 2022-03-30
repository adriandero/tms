package at.snt.tms.rest.services;

import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.repositories.status.InternalStatusRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntStatusService {
    private final InternalStatusRepository internalStates;

    @Autowired
    public IntStatusService(InternalStatusRepository internalStates) {
        this.internalStates = internalStates;
    }

    public Iterable<InternalStatus> findAll() {
        return internalStates.findAll();
    }

    public InternalStatus findById(Long id) {
        Optional<InternalStatus> intState = internalStates.findById(id);
        if (intState.isEmpty()) {
            throw new IllegalStateException("Internal status not found for given id:" + id);
        }
        return intState.get();
    }

    public void add(@Header(value = "label") String label) {
        InternalStatus intS = new InternalStatus(label);
        System.out.println("Add internal status to database: " + intS);
        internalStates.save(intS);
    }

    public void delete(Long id) {
        InternalStatus intS = findById(id);
        System.out.println("Deleted internal status: " + intS);
        internalStates.delete(intS);
    }
}