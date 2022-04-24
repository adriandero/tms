package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Class {@code Attachment}
 *
 * @author Oliver Sommer
 */
@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
