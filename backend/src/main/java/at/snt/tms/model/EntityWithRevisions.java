package at.snt.tms.model;

/**
 * Class {@code RevisionsEntity}
 * <p>
 * Simple class to store an audited entity combined with a revision of it.
 *
 * @author Oliver Sommer
 */
public class EntityWithRevisions<T> {
    private RevisionInformation revision;
    private T entity;

    public EntityWithRevisions(RevisionInformation revision, T entity) {
        this.revision = revision;
        this.entity = entity;
    }

    public RevisionInformation getRevision() {
        return revision;
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "EntityWithRevisions{" +
                "revision=" + revision +
                ", entity=" + entity +
                '}';
    }
}
