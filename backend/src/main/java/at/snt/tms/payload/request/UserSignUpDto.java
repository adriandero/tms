package at.snt.tms.payload.request;

/**
 * Class {@code UserSignUpDto}
 *
 * @author Oliver Sommer
 */
public class UserSignUpDto {  // TODO to be extended with more attributes, currently unused
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
}
