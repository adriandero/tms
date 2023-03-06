package at.snt.tms.auditing;

import at.snt.tms.model.RevisionInformation;
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
        RevisionInformation rev = (RevisionInformation) revisionEntity;
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            rev.setAuthor(author);
        }
    }
}
