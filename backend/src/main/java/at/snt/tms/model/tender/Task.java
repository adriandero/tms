package at.snt.tms.model.tender;

import javax.persistence.*;

/**
 * Class {@code AssignmentType}
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "ta_task", schema = "tms_db")
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "ta_id")
    private Long id;

    @Column(name = "ta_designation", length = 75)
    private String designation;

    public Task(String designation) {
        this.designation = designation;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
