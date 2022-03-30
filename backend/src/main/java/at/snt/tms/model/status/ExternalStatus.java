package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Class {@code ExternalStatus}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "es_external_status")
public class ExternalStatus {
    // https://stackoverflow.com/questions/28949853/is-it-possible-to-force-hibernate-to-embed-an-entity

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "es_id", nullable = false)
    private Long id;

    @Column(name = "es_label", nullable = false, length = 35)
    private String label;

    @ManyToMany
    @JoinTable(name = "es_status_transitions", joinColumns = @JoinColumn(name = "es_id", referencedColumnName = "es_id"), inverseJoinColumns = @JoinColumn(name = "transition_es_id", referencedColumnName = "es_id"))
    @JsonIgnore
    private Set<ExternalStatus> transitions;

    public ExternalStatus(String label) {
        this.label = label;
    }

    public ExternalStatus() {
    }

    public Set<ExternalStatus> getTransitions() {
        return transitions;
    }

    private void setTransitions(Set<ExternalStatus> transitions) {
        this.transitions = transitions;
    }

    public void addTransitions(ExternalStatus... transitions) {
        for(ExternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    public void addTransition(ExternalStatus transition) {
        if(transition == null) {
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

/*@Embeddable
public class EmbedExternalStatus extends ExternalStatus {

}*/
