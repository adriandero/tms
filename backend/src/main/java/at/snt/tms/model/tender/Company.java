package at.snt.tms.model.tender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class {@code Company}
 * <p>
 * Companies that commission Tenders.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "c_company")
public class Company implements Serializable {
    private static final long serialVersionUID = -2331276148933404188L;

    @Id
    @GeneratedValue
    @Column(name = "c_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "c_name", length = 75)
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
