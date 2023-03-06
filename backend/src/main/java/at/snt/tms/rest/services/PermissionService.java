package at.snt.tms.rest.services;

import at.snt.tms.model.operator.Permission;
import at.snt.tms.repositories.operator.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends GenericCrudRepoService<Permission, Long> {

    @Autowired
    public PermissionService(PermissionRepository permissions) {
        super(permissions, Permission.class);
    }

}