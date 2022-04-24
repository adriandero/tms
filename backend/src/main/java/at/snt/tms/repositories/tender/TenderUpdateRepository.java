package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.TenderUpdate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code TenderUpdateRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface TenderUpdateRepository extends CrudRepository<TenderUpdate, Long> {

}
