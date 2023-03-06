package at.snt.tms.repositories.status;

import at.snt.tms.model.status.ExternalStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code ExternalStatusRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface ExternalStatusRepository extends CrudRepository<ExternalStatus, Long> {
    ExternalStatus findByLabel(String label);
}
