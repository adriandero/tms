package at.snt.tms.repositories.operator;

import at.snt.tms.model.operator.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code RoleRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

}
