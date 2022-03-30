package at.snt.tms.model.operator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class {@code Users}
 * <p>
 * Simply a user of this system.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "u_users")
public class User implements Serializable {
    private static final long serialVersionUID = -7843067404217832070L;

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;
    @Column(name = "u_mail")
    private String mail;
    @Column(name = "u_passwordHash")
    private String passwordHash;
    @ManyToOne
    @JoinColumn(name = "u_r_role", foreignKey = @ForeignKey(name = "r_id"))
    private Role role;

    public User(String mail, Role role) {
        this.mail = mail;
        this.role = role;
    }

    public User(String mail, String passwordHash) {
        this.mail = mail;
        this.passwordHash = passwordHash;
    }

    public User(String mail, String passwordHash, Role role) {
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    private void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                '}';
    }
}
