package at.snt.tms.model.tender;

import at.snt.tms.model.status.ExternalStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Class {@code TenderUpdate}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "tu_tender_updates")
public class TenderUpdate implements Serializable {
    private static final long serialVersionUID = -5898473558184076078L;

    @Id
    @GeneratedValue
    @Column(name = "tu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tu_t_tender")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "tu_es_external_status")
    private ExternalStatus externalStatus;

    @CreationTimestamp
    @Column(name = "tu_valid_from" /*, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"*/)
    private Timestamp validFrom;

    @CreationTimestamp
    @Column(name = "tu_valid_to" /*, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"*/)
    private Timestamp validTo;

    @Column(name = "tu_details")
    private String details;

    /*@ElementCollection
    @CollectionTable(name = "tud_tender_updates_documents", joinColumns = @JoinColumn(name = "tu_id"))
    @Column(name = "tu_documents")*/
    @OneToMany(mappedBy = "tenderUpdate")
    private Set<Attachment> attachedFiles;

    public TenderUpdate(Tender tender, ExternalStatus externalStatus, Timestamp validFrom, String details, Set<Attachment> attachedFiles) {
        this.tender = tender;
        this.externalStatus = externalStatus;
        this.validFrom = validFrom;
        this.details = details;
        this.attachedFiles = attachedFiles;
    }

    public TenderUpdate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public ExternalStatus getExternalStatus() {
        return externalStatus;
    }

    public void setExternalStatus(ExternalStatus externalStatus) {
        this.externalStatus = externalStatus;
    }

    public Timestamp getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    public Timestamp getValidTo() {
        return validTo;
    }

    public void setValidTo(Timestamp validTo) {
        this.validTo = validTo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Attachment> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(Set<Attachment> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
