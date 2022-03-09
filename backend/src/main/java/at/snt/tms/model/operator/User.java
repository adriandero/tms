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
@Table(name = "u_users", schema = "tms_db")
public class User implements Serializable {
    private static final long serialVersionUID = -7843067404217832070L;

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;
    @Column(name = "u_mail", length = 50)
    private String mail;
    @ManyToOne
    @JoinColumn(name = "u_r_role", foreignKey = @ForeignKey(name = "r_id"))
    private Role role;

    public User(Long id, String mail, Role role) {
        this.id = id;
        this.mail = mail;
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

    @Override
    public String toString() {
        return "User{" + "mail='" + mail + '\'' + ", role=" + role + '}';
    }
}
