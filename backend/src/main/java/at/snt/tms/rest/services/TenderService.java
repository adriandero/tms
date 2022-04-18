package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.tender.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenderService {
    private final TenderRepository tenders;

    @Autowired
    public TenderService(TenderRepository tenders) {
        this.tenders = tenders;
    }

    public Iterable<Tender> findAll() {
        return tenders.findAll();
    }

    public Tender findById(Long id) {
        Optional<Tender> tender = tenders.findById(id);
        if (tender.isEmpty()) {
            throw new IllegalStateException("Tender not found for given id:" + id);
        }
        return tender.get();
    }
}