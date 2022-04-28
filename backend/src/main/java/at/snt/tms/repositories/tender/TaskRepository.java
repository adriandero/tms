package at.snt.tms.repositories.tender;

import at.snt.tms.model.database.tender.Task;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code TaskRepository}
 *
 * @author Oliver Sommer
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

}
