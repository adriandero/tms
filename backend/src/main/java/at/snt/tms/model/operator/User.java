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
public class User implements Serializable {  // TODO https://www.javadevjournal.com/spring-security/spring-security-roles-and-permissions/
    // TODO https://docs.spring.io/spring-security/reference/servlet/appendix/database-schema.html#_user_schema
    private static final long serialVersionUID = -7843067404217832070L;

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;
    @Column(name = "u_mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "u_first_name")
    private String firstName;
    @Column(name = "u_last_name")
    private String lastName;
    @Column(name = "u_password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "u_g_group", foreignKey = @ForeignKey(name = "g_id"))
    private Group group;

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public User(String mail, String firstName, String lastName, String password, Group group) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.group = group;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", mail='" + mail + '\'' + ", passwordHash='" + password + '\'' + ", group=" + group + '}';
    }
}
