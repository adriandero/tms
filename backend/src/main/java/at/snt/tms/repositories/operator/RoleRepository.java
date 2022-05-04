package at.snt.tms.repositories.operator;

import at.snt.tms.model.database.operator.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code RoleRepository}
 *
 * @author Oliver Sommer
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

}
