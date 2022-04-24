package at.snt.tms.repositories.operator;

import at.snt.tms.model.operator.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code RoleRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
