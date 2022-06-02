package at.snt.tms.repositories.tender;

import at.snt.tms.model.operator.User;
import at.snt.tms.model.tender.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code CompanyRepository}
 *
 * @author Oliver Sommer
 */
@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findByName(String name);
}
