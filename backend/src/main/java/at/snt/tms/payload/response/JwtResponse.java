package at.snt.tms.payload.response;

import java.util.List;

/**
 * Class {@code JwtResponse}
 *
 * @author Oliver Sommer
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String mail;
    private List<String> roles;

    public JwtResponse(String accessToken, String mail, List<String> roles) {
        this.token = accessToken;
        this.mail = mail;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<String> getRoles() {  // TODO
        return roles;
    }
}
