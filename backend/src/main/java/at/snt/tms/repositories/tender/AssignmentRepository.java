package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Assignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code AssignmentRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

    public default List<Assignment> findAssignmentsByTenderId(Long id) {
        // *** this is bad ***
        Iterable<Assignment> allAssignments = this.findAll();
        ArrayList<Assignment> allAssignmentsWithTenderId = new ArrayList<>();
        for (Assignment ass: allAssignments) {
            // should use sql / methods from repository
            if (ass.getTender() != null && ass.getTender().getId().equals(id)){
                allAssignmentsWithTenderId.add(ass);
            }
        }

        return allAssignmentsWithTenderId;
    }
}
