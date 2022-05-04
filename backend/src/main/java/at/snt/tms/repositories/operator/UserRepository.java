package at.snt.tms.repositories.operator;

import at.snt.tms.model.database.operator.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Class {@code UserRepository}
 *
 * @author Oliver Sommer
 */
public interface UserRepository extends CrudRepository<User, Long> {
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
    List<User> findByMailIgnoreCase(String mail);

    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations Example 34
}
