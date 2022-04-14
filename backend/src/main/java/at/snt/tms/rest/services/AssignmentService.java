package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Assignment;
import at.snt.tms.repositories.tender.AssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService extends GenericCrudRepoService<Assignment> {
    public AssignmentService (AssignmentRepository assignmentRepository){
        super(assignmentRepository, Assignment.class);
    }

    public void add() {
        Assignment assignment = new Assignment();
        System.out.println("Add Assignment to database: " + assignment);
        repository.save(assignment);
    }

}
