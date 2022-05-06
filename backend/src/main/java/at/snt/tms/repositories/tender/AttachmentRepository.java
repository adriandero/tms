package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code Attachment}
 *
 * @author Oliver Sommer
 */
@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
