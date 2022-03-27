package at.snt.tms.repositories.status;

import at.snt.tms.model.status.ExternalStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code ExternalStatusRepository}
 *
 * @author Oliver Sommer
 */
public interface ExternalStatusRepository extends CrudRepository<ExternalStatus, Long> {

}
