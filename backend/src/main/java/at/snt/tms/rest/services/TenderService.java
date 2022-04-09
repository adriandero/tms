package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.tender.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenderService extends GenericCrudRepoService<Tender> {

    @Autowired
    public TenderService(TenderRepository tenders){
        super(tenders, Tender.class);
    }

// pain
//    public ResponseEntity<?> findById(Long id) throws ResourceNotFoundException {
//        Optional<Tender>tender = tenders.findById(id);
//        if (tender.isEmpty())
//            return new ResponseEntity<>("Tender not found for id: " + id, HttpStatus.NOT_FOUND);
//        return ResponseEntity.ok().body(tender.get());
//        Tender tender = tenders.findById(id).orElseThrow(() ->   new ResourceNotFoundException("FUCKOFF"));
//        return ResponseEntity.ok().body(tender);
//        return tenders.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Bruh"));
//        Optional<Tender> optionalTender = tenders.findById(id);
//        if (optionalTender.isEmpty())
//            throw new ApiException(HttpStatus.NOT_FOUND, "bruh");
//                throw new EntityNotFoundException();
//        return optionalTender.get();
       // return ResponseEntity.ok().body(tender);
}