package at.snt.tms.rest.services.tender;

import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.tender.TenderRepository;
import at.snt.tms.rest.services.GenericCrudRepoService;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//@Controller
@Service  // works even without @Service interestingly?
public class TenderService extends GenericCrudRepoService<Tender, Long> {

    @Autowired
    public TenderService(TenderRepository tenders){
        super(tenders, Tender.class);
    }

    public ResponseEntity<? extends Iterable<Tender>> findFiltered(@Body FilterConfiguration config) {
        System.out.println(config);
        return super.findAll();
    }
}