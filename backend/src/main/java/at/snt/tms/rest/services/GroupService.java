package at.snt.tms.rest.services;

import at.snt.tms.model.operator.Group;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.repositories.operator.GroupRepository;
import at.snt.tms.repositories.status.InternalStatusRepository;
import org.apache.camel.Header;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class GroupService extends GenericCrudRepoService<Group, Long>{

    @Autowired
    public GroupService(GroupRepository groups) {
        super(groups, Group.class);
    }

}