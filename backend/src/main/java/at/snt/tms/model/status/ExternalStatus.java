package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code ExternalStatus}
 * <p>
 * {@code ExternalStatus} represent the "Bekanntmachungsart" of a {@code Tender}/{@code TenderUpdate}. It may bring a
 * Tender into a concluding stage, which is shown in {@link ExternalStatus#terminatesTender}. A {@code ExternalStatus}
 * has a defined list of status that succeed the current one in {@link ExternalStatus#transitions}.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "es_external_status")
public class ExternalStatus implements Serializable {
    private static final long serialVersionUID = 8773059058258923847L;

    @Id
    @GeneratedValue
    @Column(name = "es_id", nullable = false)
    private Long id;

    @Column(name = "es_label", nullable = false, unique = true, length = 600)
    private String label;

    @Column(name = "es_terminates_tender")
    private Boolean terminatesTender;

    @ManyToMany
    @JoinTable(name = "es_status_transitions", joinColumns = @JoinColumn(name = "es_id", referencedColumnName = "es_id"), inverseJoinColumns = @JoinColumn(name = "transition_es_id", referencedColumnName = "es_id"))
    @JsonIgnore
    private Set<ExternalStatus> transitions = new HashSet<>();

    public ExternalStatus(String label, Boolean terminatesTender) {
        this(label);
        this.terminatesTender = terminatesTender;
    }

    public ExternalStatus(String label) {
        this.label = label;
    }

    public ExternalStatus() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getTerminatesTender() {
        return terminatesTender;
    }

    public void setTerminatesTender(Boolean terminatesTender) {
        this.terminatesTender = terminatesTender;
    }

    public Set<ExternalStatus> getTransitions() {
        return transitions;
    }

    private void setTransitions(Set<ExternalStatus> transitions) {
        this.transitions = transitions;
    }

    public void addTransition(ExternalStatus transition) {
        if (transition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        this.transitions.add(transition);
    }

    public void addTransitions(ExternalStatus... transitions) {
        for (ExternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalStatus that = (ExternalStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(label, that.label) && Objects.equals(terminatesTender, that.terminatesTender) && Objects.equals(transitions, that.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, terminatesTender, transitions);
    }

    @Override
    public String toString() {
        return "ExternalStatus{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", terminatesTender=" + terminatesTender +
                ", transitions=" + transitions +
                '}';
    }
}
