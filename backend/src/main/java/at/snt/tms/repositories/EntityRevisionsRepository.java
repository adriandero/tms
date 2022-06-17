package at.snt.tms.repositories;

import at.snt.tms.model.EntityWithRevisions;
import at.snt.tms.model.RevisionInformation;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code EntityRevisionsRepository}
 * <p>
 * Generic revision repository for audited entities.
 *
 * @author Oliver Sommer
 */
@Repository
@Transactional
public class EntityRevisionsRepository {  // Hibernate Envers
    private static final Logger logger = LoggerFactory.getLogger(EntityRevisionsRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get all revisions of an audited entity.
     *
     * @param entityClass The class of the entity.
     * @param id          The id (primary key) of the entity of which the revisions are to be listed.
     * @param <T>         Generic parameter T representing the type of the audited entity.
     * @return All revisions.
     */
    public <T> List<EntityWithRevisions<T>> listRevisions(Class<T> entityClass, Object id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<EntityWithRevisions<T>> entityRevisions = new ArrayList<>();

        //System.out.println("Revisions: " + auditReader.getRevisions(entityClass, id));

        try {
            // https://www.divinisoft.com/blog-advanced-auditing-hibernate-envers/
            @SuppressWarnings({"unchecked"})  // Safe because the query returns a list of Object[3] having the following structure
            // - [0]: The entity-instance.
            // - [1]: The revision entity instance (where you can get the revision number and date)
            // - [2]: The revision type, e.g. ADD, MOD, DEL.
            List<Object[]> revisions = auditReader.createQuery().forRevisionsOfEntity(
                            entityClass,
                            false,  // false returns an array of entity and audit data
                            true  // selects the deleted audit rows
                    ).add(AuditEntity.id().eq(id))  // only select revisions of specific entity
                    .getResultList();

            for (Object[] rev : revisions) {
                T entity = entityClass.cast(rev[0]);
                entityRevisions.add(new EntityWithRevisions<>((RevisionInformation) rev[1], entity));
            }

            return entityRevisions;
        } catch (ClassCastException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
