package at.snt.tms.repositories.operator;

import at.snt.tms.model.operator.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class {@code UserRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByMailIgnoreCase(String mail);
    Boolean existsByMailIgnoreCase(String mail);
}
