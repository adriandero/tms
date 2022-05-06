package at.snt.tms.model;

public class EntityWithRevisions<T> {
    private RevisionsEntity revision;
    private T entity;

    public EntityWithRevisions(RevisionsEntity revision, T entity) {
        this.revision = revision;
        this.entity = entity;
    }

    public RevisionsEntity getRevision() {
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
