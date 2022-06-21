package at.snt.tms.model.dtos.request;

/**
 * Class {@code UserLoginDto}
 *
 * @author Oliver Sommer
 */
public class UserLoginDto {
    private String mail;
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDto{" + "mail='" + mail + '\'' + ", password=[PROTECTED]" + '}';
    }
}
