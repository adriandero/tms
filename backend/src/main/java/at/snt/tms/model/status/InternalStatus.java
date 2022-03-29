package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Class {@code InternalStatus}
 * <p>
 * One status can be assigned to a {@code Tender} either by a {@code User} or by the system.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "is_internal_status")
public class InternalStatus implements Serializable {
    private static final long serialVersionUID = -8736360362075978103L;

    @Id
    @GeneratedValue
    @Column(name = "is_id")
    private Long id;

    @Column(name = "is_label")
    private String label;

    @ManyToMany
    @JoinTable(name = "is_status_transitions", joinColumns = @JoinColumn(name = "is_id", referencedColumnName = "is_id"), inverseJoinColumns = @JoinColumn(name = "transition_is_id", referencedColumnName = "is_id"))
    @JsonIgnore
    private Set<InternalStatus> transitions;

    public InternalStatus(String label) {
        this.label = label;
    }

    public InternalStatus() {
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

    public Set<InternalStatus> getTransitions() {
        return transitions;
    }

    private void setTransitions(Set<InternalStatus> transitions) {
        this.transitions = transitions;
    }

    public void addTransitions(InternalStatus... transitions) {
        for(InternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    public void addTransition(InternalStatus transition) {
        if(transition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        this.transitions.add(transition);
    }
}
