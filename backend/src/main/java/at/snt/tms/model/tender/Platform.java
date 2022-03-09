package at.snt.tms.model.tender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class {@code Platform}
 * <p>
 * A platform where {@code Tenders} are published.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "p_platforms", schema = "tms_db")
public class Platform implements Serializable {
    private static final long serialVersionUID = -2331276148933404188L;

    @Id
    @GeneratedValue
    @Column(name = "p_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "p_link", length = 50)
    private String link;

    public Platform(String link) {
        this.link = link;
    }

    public Platform() {
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Platform platform = (Platform) o;
        return Objects.equals(id, platform.id) && Objects.equals(link, platform.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link);
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Platform{" + "id=" + id + ", link='" + link + '\'' + '}';
    }
}
