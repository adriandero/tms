package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.UserRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository users;
=======
@Service
public class UserService extends GenericCrudRepoService<User>{

>>>>>>> master

    @Autowired
    public UserService(UserRepository users) {
        super(users, User.class);
    }

    public void add(@Header(value = "mail") String mail, @Header(value = "password") String password) {
        password = passwordEncoder.encode(password);
        User u = new User(mail, password);
        System.out.println("Add user to database: " + u);
        repository.save(u);
    }

<<<<<<< HEAD
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
=======
>>>>>>> master
}