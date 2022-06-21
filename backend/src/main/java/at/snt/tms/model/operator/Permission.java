package at.snt.tms.model.operator;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class {@code Permission}
 * <p>
 * {@code Permission}s for {@code User}s of the system.
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

    @Column(name = "pe_designation", length = 100)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && Objects.equals(designation, that.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                '}';
    }
}
