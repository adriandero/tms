package at.snt.tms.model.mailing;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class {@code OAuthResponseDetails.java}
 * <p>
 * Class containing oauth details.
 *
 * @author Dominik Fluch
 */
public class OAuthResponseDetails {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("ext_expires_in")
    private long extExpiresIn;

    @JsonProperty("scope")
    private String scope;

    private OAuthResponseDetails() {}

    public String getAccessToken() {
        return accessToken;
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    private void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    private void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExtExpiresIn() {
        return extExpiresIn;
    }

    private void setExtExpiresIn(long extExpiresIn) {
        this.extExpiresIn = extExpiresIn;
    }

    public String getScope() {
        return scope;
    }

    private void setScope(String scope) {
        this.scope = scope;
    }

}
