package at.snt.tms.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code Group}
 * <p>
 * A {@code Group} defines a set of {@code Permission}s. Needless to say, multiple {@code User}s can be part of a
 * {@code Group}.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "g_group")
public class Group implements Serializable {
    private static final long serialVersionUID = -430763022781473677L;

    /**
     * Enum {@code Group.Static}
     * <p>
     * Enum of all static built-in groups.
     */
    public enum Static {
        MANAGER(new Group("Manager", Permission.Static.USER_MANAGEMENT.getInner())),  // admin
        ASSISTANT(new Group("Assistant"));

        private final Group inner;

        Static(Group inner) {
            this.inner = inner;
        }

        public Group getInner() {
            return inner;
        }
    }


    @Id
    @GeneratedValue
    @Column(name = "g_id")
    private Long id;

    @Column(name = "g_name", length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "g_pe_group_permissions", joinColumns = @JoinColumn(name = "g_id"), inverseJoinColumns = @JoinColumn(name = "pe_id"))
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, Permission... permissions) {
        this(name);
        this.addPermissions(permissions);
    }

    public Group() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermissions(Permission... permissions) {
        for (Permission p : permissions) {
            this.addPermission(p);
        }
    }

    public void addPermission(Permission permissions) {
        if (permissions == null) {
            throw new IllegalArgumentException("Role to be added must not be null.");
        }

        this.permissions.add(permissions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(name, group.name) && Objects.equals(permissions, group.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permissions);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
