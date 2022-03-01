package at.snt.tms.model.status;

import at.snt.tms.model.operator.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * Class {@code InternalStatus}
 * <p>
 * One status can be assigned to a {@code Tender} either by a {@code User} or by the system.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "is_internal_status", schema = "tms_db")
public class InternalStatus implements Serializable {
    private static final long serialVersionUID = -8736360362075978103L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "is_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "is_label")
    private String label;
    @ManyToOne
    @JoinColumn(name = "is_u_user", foreignKey = @ForeignKey(name = "u_mail"))
    private User user;
    @CreationTimestamp
    @Column(name = "is_timestamp", nullable = false, updatable = false)
    private Date timestamp;  // java.sql.Date

    @ManyToMany
    @JoinTable(name = "is_status_transitions",
            joinColumns = @JoinColumn(name = "is_id", referencedColumnName = "is_id"),
            inverseJoinColumns = @JoinColumn(name = "transition_is_id", referencedColumnName = "is_id")
    )
    private Set<InternalStatus> possibleTransitions;

    public InternalStatus(String label, User user) {
        this.label = label;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Set<InternalStatus> getPossibleTransitions() {
        return possibleTransitions;
    }

    private void setPossibleTransitions(Set<InternalStatus> possibleTransitions) {
        this.possibleTransitions = possibleTransitions;
    }

    public void addPossibleTransitions(InternalStatus... possibleTransitions) {
        for(InternalStatus es : possibleTransitions) {
            this.addPossibleTransition(es);
        }
    }

    public void addPossibleTransition(InternalStatus possibleTransition) {
        if(possibleTransition == null) {
            throw new IllegalArgumentException("Cannot add null-value transition.");
        }
        possibleTransitions.add(possibleTransition);
    }
}
