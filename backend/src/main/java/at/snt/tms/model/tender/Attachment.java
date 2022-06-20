package at.snt.tms.model.tender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.LobHelper;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.envers.Audited;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Class {@code Attachment}
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
    private int fileSize;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "at_file")
    private byte[] file; // We do not need to use Blob type here as attachments come in via mail meaning that they must fit into memory.

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "at_tu_update")
    private TenderUpdate tenderUpdate;

    public Attachment(String fileName, byte[] file, TenderUpdate tenderUpdate) {
        //https://stackoverflow.com/a/20622485
        this.fileName = fileName;
        this.fileSize = file.length;

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

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}
