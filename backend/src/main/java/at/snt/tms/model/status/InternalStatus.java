package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code InternalStatus}
 * <p>
 * {@code InternalStatus} can be assigned to a {@code Tender} by a {@code User} via an {@code AssignedIntStatus} object.
 * It may bring a Tender into a concluding stage, which is shown in {@link InternalStatus#terminatesTender}. A
 * {@code InternalStatus}  has a defined list of status that succeed the current one in
 * {@link InternalStatus#transitions}.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "is_internal_status")
public class InternalStatus implements Serializable {
    private static final long serialVersionUID = -8736360362075978103L;

    /**
     * Enum {@code InternalStatus.Static}
     * <p>
     * Enum of all static built-in internal states.
     *
     * @author Dominik Fluch
     */
    public enum Static {
        INTERESTING(new InternalStatus("Interesting")),
        IRRELEVANT(new InternalStatus("Irrelevant")),
        UNCHECKED(new InternalStatus("Unchecked"));

        private final InternalStatus internalStatus;

        Static(InternalStatus internalStatus) {
            this.internalStatus = internalStatus;
        }

        public InternalStatus getInternalStatus() {
            return internalStatus;
        }
    }

    @Id
    @Column(name = "is_label", length = 200)
    private String label;

    @Column(name = "is_terminates_tender")
    private Boolean terminatesTender;

    @ManyToMany
    @JoinTable(name = "is_status_transitions", joinColumns = @JoinColumn(name = "is_label", referencedColumnName = "is_label"), inverseJoinColumns = @JoinColumn(name = "transition_is_label", referencedColumnName = "is_label"))
    @JsonIgnore
    private Set<InternalStatus> transitions;

    public InternalStatus(String label) {
        this.label = label;
        this.transitions = new HashSet<>();
    }

    public InternalStatus() {
    }

    public Boolean getTerminatesTender() {
        return terminatesTender;
    }

    public void setTerminatesTender(Boolean terminatesTender) {
        this.terminatesTender = terminatesTender;
    }

    public String getLabel() {
        return label;
    }

    public InternalStatus setLabel(String label) {
        this.label = label;

        return this;
    }

    public Set<InternalStatus> getTransitions() {
        return transitions;
    }

    private void setTransitions(Set<InternalStatus> transitions) {
        this.transitions = transitions;
    }

    public void addTransitions(InternalStatus... transitions) {
        for (InternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    public void addTransition(InternalStatus transition) {
        if (transition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        this.transitions.add(transition);
    }
}
