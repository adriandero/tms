package at.snt.tms.repositories.tender;

import at.snt.tms.model.database.tender.Assignment;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code AssignmentRepository}
 *
 * @author Oliver Sommer
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

}
