package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Assignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code AssignmentRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

}
