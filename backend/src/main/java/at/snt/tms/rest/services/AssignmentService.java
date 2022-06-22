package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Assignment;
import at.snt.tms.repositories.tender.AssignmentRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService extends GenericCrudRepoService<Assignment, Long> {
    private final AssignmentRepository repository;

    public AssignmentService (AssignmentRepository assignmentRepository){
        super(assignmentRepository, Assignment.class);
        this.repository = assignmentRepository;
    }

    public void add(Assignment assignment) {
        System.out.println("Add Assignment to database: " + assignment);
        repository.save(assignment);
    }

    public ResponseEntity<?> findAssignmentsByTenderId(Long id){
        return ResponseEntity.ok(this.repository.findAssignmentsByTenderId(id));
    }

}
