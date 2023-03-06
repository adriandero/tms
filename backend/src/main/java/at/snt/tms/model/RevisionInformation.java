package at.snt.tms.model;

import at.snt.tms.auditing.CustomRevisionsListener;
import at.snt.tms.model.operator.User;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Class {@code RevisionsEntity}
 * <p>
 * Serves as table that audits the database. {@link RevisionInformation#getId()} serves as foreign key to each entry in the
 * respective auditing-tables and {@link RevisionInformation#getAuthor()} is the user that manipulated data.
 *
 * @author Oliver Sommer
 */
@Entity
@Table(name = "re_revision_info")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "re_id")),
        @AttributeOverride(name = "timestamp", column = @Column(name = "re_timestamp"))
})
@RevisionEntity(CustomRevisionsListener.class)
public class RevisionInformation extends DefaultRevisionEntity implements Serializable {
    private static final long serialVersionUID = -43063022781473677L;

    @ManyToOne
    @JoinColumn(name = "re_u_author", foreignKey = @ForeignKey(name = "u_id"))
    private User author;

    public RevisionInformation(int revisionId, Date revisionDate, User author) {
        this.setId(revisionId);
        this.setTimestamp(revisionDate.getTime());
        this.author = author;
    }

    public RevisionInformation() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "RevisionsEntity{" +
                "revisionId=" + getId() +
                ", revisionTimestamp=" + getTimestamp() +
                ", author=" + author +
                '}';
    }
}
