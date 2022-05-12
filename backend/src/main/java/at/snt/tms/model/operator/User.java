package at.snt.tms.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code Users}
 * <p>
 * Simply a user of this system.
 *
 * @author Oliver Sommer
 */
@Entity
@Audited
@Table(name = "u_users")
public class User implements UserDetails, Serializable {  // TODO https://www.javadevjournal.com/spring-security/spring-security-roles-and-permissions/
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
    //@JsonIgnore  // commented for debugging reasons. uncomment for production. TODO
    @NotAudited
    private String password;
    @ManyToOne
    @JoinColumn(name = "u_g_group", foreignKey = @ForeignKey(name = "g_id"))
    private Group group;
    @Column(name = "u_refresh_token_secret", length = 500)
    @NotAudited
    @JsonIgnore
    private String refreshTokenSecret;

    public User(String mail, String passwordHash) {
        this.mail = mail;
        this.password = passwordHash;
    }

    public User(String mail, String firstName, String lastName, String passwordHash, Group group) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = passwordHash;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {  // TODO
        Set<GrantedAuthority> authorities = new HashSet<>();
        //this.getGroup().getPermissions().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getDesignation())));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = passwordHash;
    }

    @Override
    public String getUsername() {
        return this.getMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getRefreshTokenSecret() {
        return refreshTokenSecret;
    }

    public void setRefreshTokenSecret(String refreshTokenSecret) {
        this.refreshTokenSecret = refreshTokenSecret;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", mail='" + mail + '\'' + ", password=[PROTECTED], group=" + group + '}';
    }
}
