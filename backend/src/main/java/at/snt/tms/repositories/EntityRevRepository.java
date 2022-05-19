package at.snt.tms.repositories;

import at.snt.tms.model.EntityWithRevisions;
import at.snt.tms.model.RevisionsEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class EntityRevRepository {  // Generic Revision Repository for Entities (Hibernate Envers)
    @PersistenceContext
    private EntityManager entityManager;

    public <T> List<EntityWithRevisions<T>> listRevisions(Class<T> entityClass, Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Number> revisions = auditReader.getRevisions(entityClass, id);

        List<EntityWithRevisions<T>> entityRevisions = new ArrayList<>();
        for (Number revision : revisions) {
            T rev = auditReader.find(entityClass, id, revision);
            Date revisionDate = auditReader.getRevisionDate(revision);

            entityRevisions.add(
                    new EntityWithRevisions<>(
                            new RevisionsEntity(revision.intValue(), revisionDate, null), rev));  // TODO
        }

        return entityRevisions;
    }
}
