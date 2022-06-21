package at.snt.tms.model.tender;

import at.snt.tms.model.status.ExternalStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code TenderUpdate}
 * <p>
 * A {@code TenderUpdate} represents the information released by a {@code Company} on a {@code Platform} for a specific
 * {@code Tender}. This will include a "Bekanntmachungsart" {@link TenderUpdate#externalStatus}, the validity period
 * starting with {@link TenderUpdate#validFrom} until {@link TenderUpdate#validTo}, {@link TenderUpdate#details} and
 * {@link TenderUpdate#attachedFiles}.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "tu_tender_updates")
public class TenderUpdate implements Serializable {
    private static final long serialVersionUID = -5898473558184076078L;

    @Id
    @GeneratedValue
    @Column(name = "tu_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tu_te_tender")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "tu_es_external_status")
    private ExternalStatus externalStatus;

    @CreationTimestamp
    @Column(name = "tu_valid_from" /*, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"*/)
    private Timestamp validFrom;

    @Column(name = "tu_valid_to")
    private Timestamp validTo;

    @Column(name = "tu_details")
    private String details;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tenderUpdate")
    @OrderBy(value = "fileName")
    private Set<Attachment> attachedFiles = new HashSet<>();

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

    private void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenderUpdate that = (TenderUpdate) o;
        return Objects.equals(id, that.id) && Objects.equals(tender == null ? null : tender.getId(), that.tender == null ? null : that.tender.getId()) && Objects.equals(externalStatus, that.externalStatus) && Objects.equals(validFrom, that.validFrom) && Objects.equals(validTo, that.validTo) && Objects.equals(details, that.details) && Objects.equals(attachedFiles, that.attachedFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tender == null ? null : tender.getId(), externalStatus, validFrom, validTo, details, attachedFiles);
    }

    @Override
    public String toString() {
        return "TenderUpdate{" +
                "id=" + id +
                ", tenderId=" + (tender == null ? null : tender.getId()) +
                ", externalStatus=" + externalStatus +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", details='" + details + '\'' +
                ", attachedFiles=" + attachedFiles +
                '}';
    }
}
