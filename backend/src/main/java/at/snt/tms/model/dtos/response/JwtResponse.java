package at.snt.tms.model.dtos.response;

import at.snt.tms.model.dtos.AccessRefreshTokenDto;

import java.util.List;

/**
 * Class {@code JwtResponse}
 *
 * @author Oliver Sommer
 */
public class JwtResponse {
    private AccessRefreshTokenDto tokens;
    private String type = "Bearer";
    private String mail;
    private final List<String> permissions;

    public JwtResponse(AccessRefreshTokenDto tokens, String mail, List<String> roles) {
        this.tokens = tokens;
        this.mail = mail;
        this.permissions = roles;
    }

    public AccessRefreshTokenDto getTokens() {
        return tokens;
    }

    public void setAccessToken(AccessRefreshTokenDto tokens) {
        this.tokens = tokens;
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

    public List<String> getPermissions() {
        return permissions;
    }
}
