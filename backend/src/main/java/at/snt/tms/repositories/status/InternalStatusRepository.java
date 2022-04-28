package at.snt.tms.repositories.status;

import at.snt.tms.model.database.status.InternalStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code InternalStatusRepository}
 *
 * @author Oliver Sommer
 */
public interface InternalStatusRepository extends CrudRepository<InternalStatus, Long> {

}
