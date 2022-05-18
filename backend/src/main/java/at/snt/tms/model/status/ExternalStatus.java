package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Class {@code ExternalStatus}
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "es_external_status")
public class ExternalStatus implements Serializable {
    private static final long serialVersionUID = 8773059058258923847L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "es_id", nullable = false)
    private Long id;

    @Column(name = "es_label", nullable = false, unique = true, length = 50)
    private String label;

    @Column(name = "es_terminates_tender")
    private Boolean terminatesTender;

    @ManyToMany
    @JoinTable(name = "es_status_transitions", joinColumns = @JoinColumn(name = "es_id", referencedColumnName = "es_id"), inverseJoinColumns = @JoinColumn(name = "transition_es_id", referencedColumnName = "es_id"))
    @JsonIgnore
    private Set<ExternalStatus> transitions;

    public ExternalStatus(String label) {
        this.label = label;
    }

    public ExternalStatus() {
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

    public void addTransitions(ExternalStatus... transitions) {
        for (ExternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    public void addTransition(ExternalStatus transition) {
        if (transition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        this.transitions.add(transition);
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
}
