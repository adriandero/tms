package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Assignment;
import at.snt.tms.repositories.tender.AssignmentRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssignmentService extends GenericCrudRepoService<Assignment, Long> {
    public AssignmentService (AssignmentRepository assignmentRepository){
        super(assignmentRepository, Assignment.class);
    }

    public void add(Assignment assignment) {
        System.out.println("Add Assignment to database: " + assignment);
        repository.save(assignment);
    }

    public ResponseEntity<?> findAssignmentsByTenderId(Long id){
        // *** this is bad ***
        Iterable<Assignment> allAssignments = repository.findAll();
        ArrayList<Assignment> allAssignmentsWithTenderId = new ArrayList<>();
        for (Assignment ass: allAssignments) {
            // should use sql / methods from repository
            if (ass.getTender() != null && ass.getTender().getId().equals(id)){
                allAssignmentsWithTenderId.add(ass);
            }
        }
        return ResponseEntity.ok(allAssignmentsWithTenderId);
    }

}
