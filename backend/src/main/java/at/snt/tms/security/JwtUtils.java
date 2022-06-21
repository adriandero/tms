package at.snt.tms.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code JwtUtils}
 * <p>
 * Simple helper class.
 *
 * @author Oliver Sommer
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${tms.jwt.secret}")
    private String jwtSecret;

    @Value("${tms.jwt.access-token-expiration}")
    private long jwtAccessExpiration;

    @Value("${tms.jwt.refresh-token-expiration}")
    private long jwtRefreshExpiration;

    public String generateJwtAccessToken(Authentication authentication) {
        UserDetails userPrincipal = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())  // username is mail
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String refreshExpiredJwtAccessToken(String expiredAccessToken) {
        return Jwts.builder()
                .setSubject(this.getMailFromJwtAccessToken(expiredAccessToken))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateJwtRefreshToken(String randomNumberBase64) {
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("secret", randomNumberBase64);

        return Jwts.builder()
                .addClaims(additionalClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtRefreshExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getMailFromJwtAccessToken(String token) {
        return Jwts.parser()
                .setAllowedClockSkewSeconds(jwtRefreshExpiration)
                .setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getSecretFromJwtRefreshToken(String token) {
        return Jwts.parser()
                .setAllowedClockSkewSeconds(jwtRefreshExpiration)
                .setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getOrDefault("secret", null).toString();
    }

    public boolean jwtIsRefreshable(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (SignatureException e) {
            logger.debug("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.debug("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.debug("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.debug("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.debug("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.debug("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.debug("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.debug("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.debug("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
