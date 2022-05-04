package at.snt.tms.repositories.tender;

import at.snt.tms.model.database.tender.Attachment;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code Attachment}
 *
 * @author Oliver Sommer
 */
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
