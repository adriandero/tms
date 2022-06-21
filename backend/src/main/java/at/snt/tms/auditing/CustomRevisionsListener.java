package at.snt.tms.auditing;

import at.snt.tms.model.RevisionsEntity;
import at.snt.tms.model.operator.User;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Class {@code CustomRevisionsListener}
 * <p>
 * Stores information about the {@code User} that made a change to an entity.
 *
 * @author Oliver Sommer
 */
public class CustomRevisionsListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        RevisionsEntity rev = (RevisionsEntity) revisionEntity;
        User author = (User) SecurityContextHolder.getContext().getAuthentication();
        rev.setAuthor(author);
    }
}
