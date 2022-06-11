package at.snt.tms.model.tender;

import at.snt.tms.model.operator.User;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class {@code Assignment}
 * <p>
 * {@code User}s can be assigned to certain {@code Tender}s. An {@code Assignment} entity contains this and further
 * information, what the {@code User}'s task is.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "as_assignments")
public class Assignment implements Serializable {
    private static final long serialVersionUID = -5828276887838212585L;

    @Id
    @GeneratedValue
    @Column(name = "as_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "as_te_tender")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "as_u_user")
    private User user;

    @Column(name = "as_instruction")
    private String instruction;

    @Column(name = "as_has_unseen_changes")
    private Boolean hasUnseenChanges;

    public Assignment() {
    }

    public Assignment(Tender tender, User user) {
        this.tender = tender;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Boolean getHasUnseenChanges() {
        return hasUnseenChanges;
    }

    public void setHasUnseenChanges(Boolean hasUnseenChanges) {
        this.hasUnseenChanges = hasUnseenChanges;
    }
}
