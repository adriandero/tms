package at.snt.tms.model.tender;

import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Class {@code Tender}
 * <p>
 * {@code Tender}s con be uniquely defined by their {@link Tender#documentNumber} in combination with
 * {@link Tender#platform}. {@link Tender#latestExtStatus} represents the latest status assigned to the {@code Tender}
 * by the Platform whereas {@link Tender#latestIntStatus} shows the {@code Tender}'s status for the users of the system.
 * Updates are stored in {@link Tender#updates} and the AI predicts {@link Tender#predictedIntStatus} with an accuracy
 * {@link Tender#predictionAccuracy}.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "te_tenders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tender implements Serializable {
    private static final long serialVersionUID = 3865877817478679993L;

    @Id
    @GeneratedValue
    @Column(name = "te_id")
    private Long id;

    @Column(name = "te_document_nr", length = 30, nullable = false)
    private String documentNumber;

    @ManyToOne
    @JoinColumn(name = "te_p_platform", nullable = false)
    private Platform platform;

    @Column(name = "te_link", length = 2048, nullable = false)
    private String link;

    @Column(name = "te_name", length = 2048, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "te_c_company")
    private Company company;

    @Column(name = "te_description", length = 2048)
    private String description;  // Beschreibung der Ausschreibung

    @ManyToOne
    @JoinColumn(name = "te_tu_updates")
    private TenderUpdate latestUpdate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender")
    @OrderBy(value = "validFrom")
    @NotAudited
    private Set<TenderUpdate> updates = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "te_es_latest_ext_status")
    private ExternalStatus latestExtStatus;

    @ManyToOne
    @JoinColumn(name = "te_is_latest_int_status")
    private InternalStatus latestIntStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender")
    @OrderBy(value = "created")
    @NotAudited
    private Set<AssignedIntStatus> assignedIntStatus = new HashSet<>();

    @Column(name = "te_prediction_status")
    @NotAudited
    private InternalStatus.Static predictedIntStatus;

    @JoinColumn(name = "te_prediction_accuracy")
    @NotAudited
    private Integer predictionAccuracy;

    public Tender(String documentNumber, Platform platform, String link, String name, Company company, String description, ExternalStatus latestExtStatus, InternalStatus.Static predicted, int predictionAccuracy) {
        this(documentNumber, platform, link, name, company, description, latestExtStatus);
        this.predictedIntStatus = predicted;
        this.predictionAccuracy = predictionAccuracy;
    }

    public Tender(String documentNumber, Platform platform, String link, String name, Company company, String description, ExternalStatus latestExtStatus) {
        this.documentNumber = documentNumber;
        this.platform = platform;
        this.link = link;
        this.name = name;
        this.company = company;
        this.description = description;

        this.latestExtStatus = latestExtStatus;
    }

    public Tender() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TenderUpdate getLatestUpdate() {
        assert latestUpdate.equals(updates.stream().reduce((a, b) -> a.getValidFrom().after(b.getValidFrom()) ? a : b).orElse(null));
        return latestUpdate;
    }

    void setLatestUpdate(TenderUpdate latestUpdate) {
        this.latestUpdate = latestUpdate;
        this.setLatestExtStatus(latestUpdate.getExternalStatus());
    }

    public void addUpdate(TenderUpdate update) {
        this.updates.add(update);
        this.setLatestUpdate(update);
        this.setLatestExtStatus(update.getExternalStatus());
    }

    public Set<TenderUpdate> getUpdates() {
        return updates;
    }

    public void setUpdates(Set<TenderUpdate> updates) {
        this.updates = updates;

        Optional<TenderUpdate> latest = updates.stream().reduce((a, b) -> a.getValidFrom().after(b.getValidFrom()) ? a : b);
        latest.ifPresent(this::setLatestUpdate);
    }

    public ExternalStatus getLatestExtStatus() {
        return latestExtStatus;
    }

    public void setLatestExtStatus(ExternalStatus latestExtStatus) {
        this.latestExtStatus = latestExtStatus;
    }

    public InternalStatus getLatestIntStatus() {
        return latestIntStatus;
    }

    public void setLatestIntStatus(InternalStatus latestIntStatus) {
        this.latestIntStatus = latestIntStatus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void addAssignedIntStatus(AssignedIntStatus assignedIntStatus) {
        this.assignedIntStatus.add(assignedIntStatus);
        this.setLatestIntStatus(assignedIntStatus.getInternalStatus());
    }

    public Set<AssignedIntStatus> getAssignedIntStatus() {
        return assignedIntStatus;
    }

    public void setAssignedIntStatus(Set<AssignedIntStatus> assignedIntStatus) {
        this.assignedIntStatus = assignedIntStatus;

        Optional<AssignedIntStatus> latest = assignedIntStatus.stream().reduce((a, b) -> a.getCreated().after(b.getCreated()) ? a : b);
        latest.ifPresent(intStatus -> this.setLatestIntStatus(intStatus.getInternalStatus()));
    }

    public InternalStatus.Static getPredictedIntStatus() {
        return predictedIntStatus;
    }

    public void setPredictedIntStatus(InternalStatus.Static predictedIntStatus) {
        this.predictedIntStatus = predictedIntStatus;
    }

    public int getPredictionAccuracy() {
        return predictionAccuracy;
    }

    public void setPredictionAccuracy(int predictionAccuracy) {
        this.predictionAccuracy = predictionAccuracy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tender tender = (Tender) o;
        return Objects.equals(id, tender.id) && Objects.equals(documentNumber, tender.documentNumber) && Objects.equals(platform, tender.platform) && Objects.equals(link, tender.link) && Objects.equals(name, tender.name) && Objects.equals(company, tender.company) && Objects.equals(description, tender.description) && Objects.equals(latestUpdate, tender.latestUpdate) && Objects.equals(updates, tender.updates) && Objects.equals(latestExtStatus, tender.latestExtStatus) && Objects.equals(latestIntStatus, tender.latestIntStatus) && Objects.equals(assignedIntStatus, tender.assignedIntStatus) && predictedIntStatus == tender.predictedIntStatus && Objects.equals(predictionAccuracy, tender.predictionAccuracy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentNumber, platform, link, name, company, description, latestUpdate, updates, latestExtStatus, latestIntStatus, assignedIntStatus, predictedIntStatus, predictionAccuracy);
    }

    @Override
    public String toString() {
        return "Tender{" +
                "id=" + id +
                ", documentNumber='" + documentNumber + '\'' +
                ", platform=" + platform +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", description='" + description + '\'' +
                ", latestUpdate=" + latestUpdate +
                ", updates=" + updates +
                ", latestExtStatus=" + latestExtStatus +
                ", latestIntStatus=" + latestIntStatus +
                ", assignedIntStatuses=" + assignedIntStatus +
                ", predictedIntStatus=" + predictedIntStatus +
                ", predictionAccuracy=" + predictionAccuracy +
                '}';
    }

    public static class Builder {  // Builder-Pattern
        private final String documentNumber;
        private final Platform platform;
        private String link;
        private String name;
        private Company company;
        private String description;
        // adding updates is not supported by the Builder
        private ExternalStatus latestExtStatus;
        // assigning InternalStatus is not supported by the Builder
        private InternalStatus.Static predictedIntStatus;
        private Integer predictionAccuracy;

        public Builder(String documentNumber, Platform platform) {
            this.documentNumber = documentNumber;
            this.platform = platform;
        }

        public static Builder newInstance(String documentNumber, Platform platform) {
            return new Builder(documentNumber, platform);
        }

        public Tender.Builder link(String link) {
            this.link = link;
            return this;
        }

        public Tender.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Tender.Builder company(Company company) {
            this.company = company;
            return this;
        }

        public Tender.Builder description(String description) {
            this.description = description;
            return this;
        }

        public Tender.Builder latestExtStatus(ExternalStatus latestExtStatus) {
            this.latestExtStatus = latestExtStatus;
            return this;
        }

        public Tender.Builder prediction(InternalStatus.Static predictedIntStatus, int predictionAccuracy) {
            this.predictedIntStatus = predictedIntStatus;
            this.predictionAccuracy = predictionAccuracy;
            return this;
        }

        public Tender build() {
            return new Tender(documentNumber, platform, link, name, company, description, latestExtStatus,
                    predictedIntStatus, predictionAccuracy);
        }
    }
}
