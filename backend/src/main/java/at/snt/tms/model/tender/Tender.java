package at.snt.tms.model.tender;

import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code Tender}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "te_tenders")
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

    @Column(name = "te_link", length = 150, nullable = false)
    private String link;

    @Column(name = "te_name", length = 150, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "te_c_company")
    private Company company;

    @Column(name = "te_description", length = 500)
    private String description;  // Beschreibung der Ausschreibung

    @ManyToOne
    @JoinColumn(name = "te_tu_updates")
    private TenderUpdate latestUpdate;

    // TODO: See if fetchtype is fine here.

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender")
    @OrderBy(value = "validFrom")
    //@JoinColumn(name = "t_tu_updates")
    private Set<TenderUpdate> updates;

    @ManyToOne
    @JoinColumn(name = "te_es_latest_ext_status")
    private ExternalStatus latestExtStatus;

    @ManyToOne
    @JoinColumn(name = "te_is_latest_int_status")
    private InternalStatus latestIntStatus;


    // TODO: See if fetch type is fine here.
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender")
    @OrderBy(value = "created")
    //@JoinColumn(name = "t_ais_assigned_int_statuses")
    private Set<AssignedIntStatus> assignedIntStatuses;

    public Tender(Long id, String documentNumber, Platform platform, String link, String name, Company company, String description, ExternalStatus latestExtStatus, InternalStatus latestIntStatus) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.platform = platform;
        this.link = link;
        this.name = name;
        this.company = company;
        this.description = description;

        // TODO: See if this is fine (just here for demo for now):
        this.latestExtStatus = latestExtStatus;
        this.latestIntStatus = latestIntStatus;
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
        return latestUpdate;
    }

    public void setLatestUpdate(TenderUpdate latestUpdate) {
        this.latestUpdate = latestUpdate;
        this.updates.add(this.latestUpdate);
    }

    public Set<TenderUpdate> getUpdates() {
        return updates;
    }

    public ExternalStatus getLatestExtStatus() {
        return latestExtStatus;
    }

    void setLatestExtStatus(ExternalStatus latestExtStatus) {
        this.latestExtStatus = latestExtStatus;
    }

    public InternalStatus getLatestIntStatus() {
        return latestIntStatus;
    }

    void setLatestIntStatus(InternalStatus latestIntStatus) {
        this.latestIntStatus = latestIntStatus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<AssignedIntStatus> getAssignedIntStatuses() {
        return assignedIntStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tender tender = (Tender) o;
        return Objects.equals(id, tender.id) && Objects.equals(documentNumber, tender.documentNumber) && Objects.equals(platform, tender.platform) && Objects.equals(link, tender.link) && Objects.equals(name, tender.name) && Objects.equals(description, tender.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentNumber, platform, link, name, description);
    }

    @Override
    public String toString() {
        return "Tender{" + "id=" + id + ", documentNumber='" + documentNumber + '\'' + ", platform=" + platform + ", link='" + link + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }

    public static class Builder {  // Builder-Pattern TODO
        private final Tender tender = new Tender();

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Tender build() {
            return tender;
        }
    }
}
