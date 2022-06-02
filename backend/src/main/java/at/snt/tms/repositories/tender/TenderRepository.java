package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Company;
import at.snt.tms.model.tender.Platform;
import at.snt.tms.model.tender.Tender;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code TenderRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface TenderRepository extends PagingAndSortingRepository<Tender, Long> {
    Tender findByDocumentNumberAndPlatform(String documentNumber, Platform platform);
}
