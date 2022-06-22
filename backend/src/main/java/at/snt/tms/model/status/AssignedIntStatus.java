package at.snt.tms.model.status;

import at.snt.tms.model.operator.User;
import at.snt.tms.model.tender.Tender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Class {@code AssignedIntStatus}
 * <p>
 * {@code User}s may assign an {@code InternalStatus} to a {@code Tender}, which is represented in an
 * {@code AssignedIntStatus} object.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "ais_assigned_int_status")
public class AssignedIntStatus implements Serializable {
    private static final long serialVersionUID = -2353155447689327872L;

    @Id
    @GeneratedValue
    @Column(name = "ais_")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ais_is_int_status")
    private InternalStatus internalStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ais_t_tender")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "ais_u_id")
    private User user;

    @CreationTimestamp
    @Column(name = "ais_created")
    private Timestamp created;

    public AssignedIntStatus(InternalStatus internalStatus, Tender tender, User user, Timestamp created) {
        this.internalStatus = internalStatus;
        this.tender = tender;
        this.user = user;
        this.created = created;
    }

    public AssignedIntStatus() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public InternalStatus getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(InternalStatus internalStatus) {
        this.internalStatus = internalStatus;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedIntStatus that = (AssignedIntStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(internalStatus, that.internalStatus) && Objects.equals(tender == null ? null : tender.getId(), that.tender == null ? null : that.tender.getId()) && Objects.equals(user, that.user) && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, internalStatus, tender == null ? null : tender.getId(), user, created);
    }

    @Override
    public String toString() {
        return "AssignedIntStatus{" +
                "id=" + id +
                ", internalStatus=" + internalStatus +
                ", tenderId=" + (tender == null ? null : tender.getId()) +
                ", user=" + user +
                ", created=" + created +
                '}';
    }
}
