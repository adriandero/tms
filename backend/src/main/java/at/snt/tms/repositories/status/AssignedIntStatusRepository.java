package at.snt.tms.repositories.status;

import at.snt.tms.model.database.status.AssignedIntStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code AssignedIntStatusRepository}
 *
 * @author Oliver Sommer
 */
public interface AssignedIntStatusRepository extends CrudRepository<AssignedIntStatus, Long> {

}
