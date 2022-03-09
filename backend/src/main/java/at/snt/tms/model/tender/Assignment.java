package at.snt.tms.model.tender;

import at.snt.tms.model.operator.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Class {@code Assignment}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "as_assignments", schema = "tms_db")
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

    @ManyToMany
    @JoinTable(name = "ast_assignment_tasks",
            joinColumns = @JoinColumn(name = "ast_as_assignment", referencedColumnName = "as_id"),
            inverseJoinColumns = @JoinColumn(name = "ast_ta_type", referencedColumnName = "ta_id")
    )
    private Set<Task> tasks;

    public Assignment() {
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

    public Set<Task> getTasks() {
        return tasks;
    }
}
