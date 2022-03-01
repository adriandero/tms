package at.snt.tms.model;

import at.snt.tms.model.status.ExternalStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import javax.persistence.JoinColumn;

/**
 * Class {@code Tender}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "t_tenders", schema = "tms_db")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Tender implements Serializable { // TODO @IdClass?
    private static final long serialVersionUID = 3865877817478679993L;
    // https://hibernate.org/orm/
    // https://stackabuse.com/a-guide-to-jpa-with-hibernate-relationship-mapping/
    @Id
    @GeneratedValue
    @Column(name = "t_id")
    private Long id;  //https://stackoverflow.com/questions/1212058/how-to-make-a-composite-primary-key-java-persistence-annotation

    @NaturalId
    @CreationTimestamp
    @Column(name = "t_timestamp", nullable = false, updatable = false /*, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"*/)
    private Timestamp timestamp;  // DB stores Unix-Timestamps -> no milliseconds
    @NaturalId
    @Column(name = "t_document_nr", length = 30, nullable = false)
    private String documentNumber;
    @NaturalId
    @ManyToOne
    @JoinColumn(name = "t_p_platform", nullable = false, foreignKey = @ForeignKey(name = "p_id"))
    private Platform platform;

    @Column(name = "t_link", length = 150, nullable = false)
    private String link;
    @Column(name = "t_name", length = 150, nullable = false)
    private String name;
    //@Column(name = "te_external_status")
    //private ExternalStatus exStatus;
    @Column(name = "t_description", length = 500)
    private String description;  // Beschreibung der Ausschreibung

    public Tender(Timestamp timestamp, String documentNumber, Platform platform, String link, String name, ExternalStatus exStatus, String description) {
        this.timestamp = timestamp;
        this.documentNumber = documentNumber;
        this.platform = platform;

        this.link = link;
        this.name = name;
        //this.exStatus = exStatus;
        this.description = description;
    }

    public Tender() {
    }

    public static List<Tender> timelineOf(Tender t) {
        return null;  // TODO
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Tender tender = (Tender) o;
        return /*Objects.equals(timestamp, tender.timestamp) &&*/ Objects.equals(documentNumber, tender.documentNumber) && Objects.equals(platform, tender.platform) && Objects.equals(name, tender.name) && Objects.equals(link, tender.link) && Objects.equals(description, tender.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, documentNumber, platform, name, link, description);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /*public ExternalStatus getExStatus() {
        return exStatus;
    }

    public void setExStatus(ExternalStatus exStatus) {
        this.exStatus = exStatus;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tender{" + "id=" + id + ", timestamp=" + timestamp + ", documentNumber='" + documentNumber + '\'' + ", platform=" + platform + ", link='" + link + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
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
