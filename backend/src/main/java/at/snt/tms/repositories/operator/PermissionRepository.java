package at.snt.tms.repositories.operator;

import at.snt.tms.model.operator.Permission;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code RoleRepository}
 *
 * @author Oliver Sommer
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
