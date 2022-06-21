package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Assignment;
import at.snt.tms.model.tender.Attachment;
import at.snt.tms.repositories.tender.AssignmentRepository;
import at.snt.tms.repositories.tender.AttachmentRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Class {@code AttachmentService.java}
 * <p>
 * Attachment service.
 *
 * @author Dominik Fluch
 */
@Service
public class AttachmentService extends GenericCrudRepoService<Attachment, Long> {

    public AttachmentService(AttachmentRepository attachmentRepository){
        super(attachmentRepository, Attachment.class);
    }

    /**
     * @param attachment
     * @return the data of the attachment with the given id.
     */
    public ResponseEntity<Resource> findContent(Long attachment) throws SQLException {
        final Attachment body = this.findById(attachment).getBody();

        return ResponseEntity.ok()
                .contentLength(body.getFileSize())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(body.getFile()));
    }
}
