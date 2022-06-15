package at.snt.tms.model.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
        INTERESTING(new InternalStatus("Interesting", false)),
        IRRELEVANT(new InternalStatus("Irrelevant", false)),
        UNCHECKED(new InternalStatus("Unchecked", false));

        private final InternalStatus internalStatus;

        Static(InternalStatus internalStatus) {
            this.internalStatus = internalStatus;
        }

        public InternalStatus getInternalStatus() {
            return internalStatus;
        }
    }

    @Id
    @GeneratedValue
    @Column(name = "is_id", nullable = false)
    private Long id;

    @Column(name = "is_label", length = 600)
    private String label;

    @Column(name = "is_terminates_tender")
    private Boolean terminatesTender;

    @ManyToMany
    @JoinTable(name = "is_status_transitions", joinColumns = @JoinColumn(name = "is_id", referencedColumnName = "is_id"), inverseJoinColumns = @JoinColumn(name = "transition_is_id", referencedColumnName = "is_id"))
    @JsonIgnore
    private Set<InternalStatus> transitions = new HashSet<>();

    public InternalStatus(String label, Boolean terminatesTender) {
        this(label);
        this.terminatesTender = terminatesTender;
    }

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

    public InternalStatus setLabel(String label) {
        this.label = label;

        return this;
    }

    public Boolean getTerminatesTender() {
        return terminatesTender;
    }

    public void setTerminatesTender(Boolean terminatesTender) {
        this.terminatesTender = terminatesTender;
    }

    public Set<InternalStatus> getTransitions() {
        return transitions;
    }

    private void setTransitions(Set<InternalStatus> transitions) {
        this.transitions = transitions;
    }

    public void addTransition(InternalStatus transition) {
        if (transition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        this.transitions.add(transition);
    }

    public void addTransitions(InternalStatus... transitions) {
        for (InternalStatus es : transitions) {
            this.addTransition(es);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalStatus that = (InternalStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(label, that.label) && Objects.equals(terminatesTender, that.terminatesTender) && Objects.equals(transitions, that.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, terminatesTender, transitions);
    }

    @Override
    public String toString() {
        return "InternalStatus{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", terminatesTender=" + terminatesTender +
                ", transitions=" + transitions +
                '}';
    }
}
