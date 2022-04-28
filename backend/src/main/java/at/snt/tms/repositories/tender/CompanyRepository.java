package at.snt.tms.repositories.tender;

import at.snt.tms.model.database.tender.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code CompanyRepository}
 *
 * @author Oliver Sommer
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {

}
