package at.snt.tms.model.operator;

import org.hibernate.envers.Audited;

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
@Audited
@Table(name = "pe_permissions")
public class Permission implements Serializable {
    private static final long serialVersionUID = -430763022781473677L;

    @Id
    @GeneratedValue
    @Column(name = "pe_id")
    private Long id;
    @Column(name = "pe_designation", length = 50)
    private String designation;

    public Permission(String designation) {
        this.designation = designation;
    }

    public Permission() {
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
