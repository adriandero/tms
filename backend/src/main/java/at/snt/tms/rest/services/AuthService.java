package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.model.dtos.AccessRefreshTokenDto;
import at.snt.tms.model.dtos.request.UserLoginDto;
import at.snt.tms.model.dtos.response.JwtResponse;
import at.snt.tms.model.dtos.response.MessageResponse;
import at.snt.tms.repositories.operator.PermissionRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.security.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code AuthService}
 *
 * @author Oliver Sommer
 */
@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${tms.jwt.refresh-token-expiration}")
    private long jwtRefreshExpiration;

    public ResponseEntity<?> authenticateUser(@Body UserLoginDto loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.info("Invalid credentials used for authentication.");
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid credentials."));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> permissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateJwtAccessToken(authentication);

        // the user must exist, otherwise it would be an invalid state -> throw exception
        User user = userRepository.findByMailIgnoreCase(userDetails.getUsername()).orElseThrow();

        Timestamp currentTime = Timestamp.from(Instant.now());

        // if a valid refresh token still exists (in a parallel session) -> send this one; otherwise send newly generated token
        if(user.getRefreshTokenSecret() == null || currentTime.getTime() - user.getRefreshTokenLatestAccess().getTime() > jwtRefreshExpiration) {
            String secret = randomBase64();

            // store new refresh-token in database
            user.setRefreshTokenSecret(secret);
            userRepository.save(user);
        }

        String refreshToken = jwtUtils.generateJwtRefreshToken(user.getRefreshTokenSecret());

        return ResponseEntity.ok(
                new JwtResponse(
                        new AccessRefreshTokenDto(accessToken, refreshToken),
                        userDetails.getUsername(),  // username is mail
                        permissions)
        );
    }

    public ResponseEntity<?> logoutUser() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == User.class)  {
            // delete refresh-token-secret for logged-in users
            User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // user.setRefreshTokenSecret(null);  // commented as this line would effectively log out all active sessions
            userRepository.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("Successfully logged out."));
    }

    public ResponseEntity<?> refreshTokens(@Body AccessRefreshTokenDto tokens) {
        if (!jwtUtils.jwtIsRefreshable(tokens.getAccessToken()) || !jwtUtils.validateJwt(tokens.getRefreshToken())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid tokens provided."));
        }

        Optional<User> optUser = userRepository.findByMailIgnoreCase(jwtUtils.getMailFromJwtAccessToken(tokens.getAccessToken()));

        // check if the secret in the refresh-token is correct
        if (optUser.isEmpty() || optUser.get().getRefreshTokenSecret() == null || !optUser.get().getRefreshTokenSecret().equals(jwtUtils.getSecretFromJwtRefreshToken(tokens.getRefreshToken()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid tokens provided."));
        }

        /* this code has been commented since regenerating the secret of refresh-tokens would invalidate refresh-tokens of other sessions
        String secret = randomBase64();  // generate random Base64-value
        // store new refresh-token in database
        user.setRefreshTokenSecret(secret);
        userRepository.save(user);
        */
        User user = optUser.get();
        user.setRefreshTokenLatestAccess(Timestamp.from(Instant.now()));

        String refresh = jwtUtils.generateJwtRefreshToken(user.getRefreshTokenSecret());
        String access = jwtUtils.refreshExpiredJwtAccessToken(tokens.getAccessToken());

        return ResponseEntity.ok(new AccessRefreshTokenDto(access, refresh));
    }

    public ResponseEntity<?> validateTokens(@Body AccessRefreshTokenDto tokens) {
        if (!jwtUtils.jwtIsRefreshable(tokens.getAccessToken()) || !jwtUtils.validateJwt(tokens.getRefreshToken())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid tokens provided."));
        }

        return ResponseEntity.ok(new MessageResponse("Valid tokens provided"));
    }


    private String randomBase64() {
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
