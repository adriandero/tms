package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.UserRepository;

import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
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

    public void add(@Header(value = "mail") String mail, @Header(value = "password") String password) {
        User u = new User(mail, password);
        System.out.println("Add user to database: " + u);
        users.save(u);
    }

    public void delete(Long id){
        User u = findById(id);
        System.out.println("Deleted user: " + u);
        users.delete(u);
    }
}