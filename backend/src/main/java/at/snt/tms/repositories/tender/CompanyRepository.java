package at.snt.tms.repositories.tender;

import at.snt.tms.model.tender.Company;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class {@code CompanyRepository}
 *
 * @author Oliver Sommer
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {

}
