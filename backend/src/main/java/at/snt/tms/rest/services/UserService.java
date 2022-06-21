package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.UserRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository users;

    @Autowired
    public UserService(UserRepository users) {
        this.users = users;
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

    public ResponseEntity<?> findByMail(String mail){
        // not best practice
        for(User user : this.findAll()){
            if (user.getMail().equals(mail))
                return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.notFound().build();
    }

    public void add(@Header(value = "mail") String mail, @Header(value = "password") String password) {
        password = passwordEncoder.encode(password);
        User u = new User(mail, password);
        System.out.println("Add user to database: " + u);
        users.save(u);
    }

    public void delete(Long id){
        User u = findById(id);
        System.out.println("Deleted user: " + u);
        users.delete(u);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByMailIgnoreCase(username);  // username is mail

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}