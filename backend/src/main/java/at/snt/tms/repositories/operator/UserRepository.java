package at.snt.tms.repositories.operator;

import at.snt.tms.model.operator.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code UserRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
    User findByMailIgnoreCase(String mail);
    Boolean existsByMailIgnoreCase(String mail);

    // TODO https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations Example 34
}
