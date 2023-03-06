package at.snt.tms.model.dtos;

/**
 * Class {@code AccessRefreshTokenDto}
 * <p>
 * A DTO for JWT access and refresh tokens.
 *
 * @author Oliver Sommer
 */
public class AccessRefreshTokenDto {
    private String accessToken;
    private String refreshToken;

    public AccessRefreshTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AccessRefreshTokenDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
