package at.snt.tms.repositories.status;

import at.snt.tms.model.status.InternalStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code InternalStatusRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface InternalStatusRepository extends CrudRepository<InternalStatus, Long> {

}
