package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.payload.request.UserLoginDto;
import at.snt.tms.payload.request.UserSignUpDto;
import at.snt.tms.payload.response.JwtResponse;
import at.snt.tms.payload.response.MessageResponse;
import at.snt.tms.repositories.operator.PermissionRepository;
import at.snt.tms.repositories.operator.UserRepository;
import at.snt.tms.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
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

    public ResponseEntity<?> authenticateUser(@Body UserLoginDto loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));
        } catch(BadCredentialsException e) {
            logger.info("Invalid credentials used for authentication.");
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid credentials."));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        JwtResponse response = new JwtResponse(jwt, userDetails.getUsername(),  // username is mail
                roles);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> registerUser(@Body UserSignUpDto signUpDto) {  // unimplemented
        if(userRepository.existsByMailIgnoreCase(signUpDto.getMail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Mail is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpDto.getMail(), encoder.encode(signUpDto.getPassword()));

        // add roles/permissions

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
