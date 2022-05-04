package at.snt.tms.repositories.tender;

import at.snt.tms.model.database.tender.Tender;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Class {@code TenderRepository}
 *
 * @author Oliver Sommer
 */
public interface TenderRepository extends PagingAndSortingRepository<Tender, Long> {

}
