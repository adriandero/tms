package at.snt.tms.rest.services;

import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.repositories.status.ExternalStatusRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExtStatusService {
    private final ExternalStatusRepository externalStates;

    @Autowired
    public ExtStatusService(ExternalStatusRepository externalStates) {
        this.externalStates = externalStates;
    }

    public Iterable<ExternalStatus> findAll() {
        return externalStates.findAll();
    }

    public ExternalStatus findById(Long id) {
        Optional<ExternalStatus> extS = externalStates.findById(id);
        if (extS.isEmpty()) {
            throw new IllegalStateException("External status not found for given id:" + id);
        }
        return extS.get();
    }

    public void add(@Header(value = "label") String label) {
        ExternalStatus extS = new ExternalStatus(label);
        System.out.println("Add external status to database: " + extS);
        externalStates.save(extS);
    }

    public void delete(Long id) {
        ExternalStatus extS = findById(id);
        System.out.println("Deleted external status: " + extS);
        externalStates.delete(extS);
    }
}