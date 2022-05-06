package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Platform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code PlatformRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface PlatformRepository extends CrudRepository<Platform, Long> {

}
