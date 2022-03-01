package at.snt.tms.model.operator;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class {@code Role}
 * <p>
 * An {@code User} can be assigned a {@code Role} which defines certain permissions within the system.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "r_roles", schema = "tms_db")
public class Role implements Serializable {
    private static final long serialVersionUID = -430763022781473677L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "r_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "r_designation", length = 50)
    private String designation;

    // private List<?> permissions;  // table for permissions?

    public Role(String designation) {
        this.designation = designation;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
