package at.snt.tms.repositories.tender;

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

}
