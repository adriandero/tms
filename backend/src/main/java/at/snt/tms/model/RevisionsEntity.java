package at.snt.tms.model;

import at.snt.tms.auditing.MyRevisionsListener;
import at.snt.tms.model.operator.User;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "re_revisions_entity")
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "re_timestamp")),
        @AttributeOverride(name = "id", column = @Column(name = "re_id"))})
@RevisionEntity(MyRevisionsListener.class)
public class RevisionsEntity extends DefaultRevisionEntity implements Serializable {
    private static final long serialVersionUID = -43063022781473677L;

    @Column(name = "re_author")
    private User author;

    public RevisionsEntity(int revisionId, Date revisionDate, User author) {
        this.setId(revisionId);
        this.setTimestamp(revisionDate.getTime());
        this.author = author;
    }

    public RevisionsEntity() {
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
