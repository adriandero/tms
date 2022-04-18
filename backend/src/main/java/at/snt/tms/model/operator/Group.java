package at.snt.tms.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code Group}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "g_group")
public class Group implements Serializable {
    private static final long serialVersionUID = -430763022781473677L;

    @Id
    @GeneratedValue
    @Column(name = "g_id")
    private Long id;
    @Column(name = "g_name", length = 50)
    private String name;
    @ManyToMany
    @JoinTable(name = "g_r_group_permissions", joinColumns = @JoinColumn(name = "g_id"), inverseJoinColumns = @JoinColumn(name = "pe_id"))
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
        for(Permission p : permissions) {
            this.addPermission(p);
        }
    }

    public void addPermission(Permission permissions) {
        if(permissions == null) {
            throw new IllegalArgumentException("Role to be added must not be null.");
        }

        this.permissions.add(permissions);
    }
}
