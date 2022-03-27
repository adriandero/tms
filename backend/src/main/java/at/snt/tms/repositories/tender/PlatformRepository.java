package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Platform;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class {@code PlatformRepository}
 *
 * @author Oliver Sommer
 */
public interface PlatformRepository extends CrudRepository<Platform, Long> {

}
