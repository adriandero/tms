package at.snt.tms.rest.services;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.UserRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericCrudRepoService<User>{


    @Autowired
    public UserService(UserRepository users) {
        super(users, User.class);
    }

    public void add(@Header(value = "mail") String mail, @Header(value = "password") String password) {
        User u = new User(mail, password);
        System.out.println("Add user to database: " + u);
        repository.save(u);
    }

}