package at.snt.tms.model.tender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Class {@code Attachment}
 * <p>
 * {@code Attachment}s are linked to {@code TenderUpdate}s, in which some additional files might be contained
 * (PDFs, ...). An {@code Attachment} entity stores these files as binary large objects.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "at_attachments")
public class Attachment implements Serializable {
    private static final long serialVersionUID = -2001662624259726346L;

    @Id
    @GeneratedValue
    @Column(name = "at_id")
    private Long id;

    @Column(name = "at_file_name")
    private String fileName;

    @Column(name = "at_file_size")
    private Long fileSize;

    @JsonIgnore
    @Lob
    @Column(name = "at_file")
    private Blob file;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "at_tu_update")
    private TenderUpdate tenderUpdate;

    public Attachment(String fileName, Blob file, TenderUpdate tenderUpdate) {
        //https://stackoverflow.com/a/20622485
        this.fileName = fileName;
        try {
            this.fileSize = file.length();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.file = file;
        this.tenderUpdate = tenderUpdate;
    }

    public Attachment() {
    }

    public TenderUpdate getTenderUpdate() {
        return tenderUpdate;
    }

    public void setTenderUpdate(TenderUpdate tenderUpdate) {
        this.tenderUpdate = tenderUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }
}
