package at.snt.tms.repositories.status;

import at.snt.tms.model.status.AssignedIntStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code AssignedIntStatusRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface AssignedIntStatusRepository extends CrudRepository<AssignedIntStatus, Long> {

}
