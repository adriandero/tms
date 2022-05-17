package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.tender.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Controller
@Service  // works even without @Service interestingly?
public class TenderService extends GenericCrudRepoService<Tender, Long> {

    @Autowired
    public TenderService(TenderRepository tenders){
        super(tenders, Tender.class);
    }
}