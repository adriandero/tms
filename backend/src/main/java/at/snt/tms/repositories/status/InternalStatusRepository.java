package at.snt.tms.repositories.status;

import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.InternalStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code InternalStatusRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface InternalStatusRepository extends CrudRepository<InternalStatus, String> {

    /**
     * @return the standard rejected internal status.
     */
    public default InternalStatus rejected() {
        return this.findById("Rejected").orElseGet(() -> this.save(new InternalStatus("Rejected")));
    }

    /**
     * @return the standard interesting internal status.
     */
    public default InternalStatus interesting() {
        return this.findById("Interesting").orElseGet(() -> this.save(new InternalStatus("Interesting")));
    }

}
