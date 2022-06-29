package at.snt.tms.rest.services;

import at.snt.tms.model.dtos.request.UserSignUpDto;
import at.snt.tms.model.dtos.response.MessageResponse;
import at.snt.tms.model.operator.Group;
import at.snt.tms.model.operator.Permission;
import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.GroupRepository;
import at.snt.tms.repositories.operator.UserRepository;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository users;
    private final GroupRepository groups;

    @Autowired
    public UserService(UserRepository users, GroupRepository groups) {
        this.users = users;
        this.groups = groups;
    }

    public Iterable<User> findAll() {
        return users.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = users.findById(id);
        if (user.isEmpty()) {
            throw new IllegalStateException("User not found for given id:" + id);
        }
        return user.get();
    }

    public ResponseEntity<?> findByMail(String mail) {
        Optional<User> user = users.findByMailIgnoreCase(mail);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user.get());
    }

    public ResponseEntity<?> add(@Body UserSignUpDto userInfo) {
        if (!hasUserManagementPermission()) {
            logger.info("Failed attempt to add user due to insufficient permissions.");
            return ResponseEntity.badRequest().body(new MessageResponse("Insufficient permissions!"));
        }

        if (userInfo.getGroupId() == null || !groups.existsById(userInfo.getGroupId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid group-id provided."));
        }

        Group group = groups.findById(userInfo.getGroupId()).orElse(null);

        User u = new User(userInfo.getMail(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                passwordEncoder.encode(userInfo.getRawPassword()),
                group
        );
        logger.info("Added user " + u + " to database.");
        users.save(u);
        return ResponseEntity.ok(new MessageResponse("Successfully added user."));
    }

    public ResponseEntity<?> delete(Long id) {
        if (!hasUserManagementPermission()) {
            logger.info("Failed attempt to delete user due to insufficient permissions.");
            return ResponseEntity.badRequest().body(new MessageResponse("Insufficient permissions!"));
        }

        User u = findById(id);
        logger.info("Deleted user " + u + " from database.");
        users.delete(u);
        return ResponseEntity.ok(new MessageResponse("Successfully deleted user."));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = users.findByMailIgnoreCase(username);  // username is mail
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }

    boolean hasUserManagementPermission() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getGroup().getPermissions().contains(Permission.Static.USER_MANAGEMENT.getInner());
        }
        return false;
    }
}