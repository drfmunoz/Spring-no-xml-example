package org.cellcore.code.dao;

import org.cellcore.code.model.AbstractJPAEntity;
import org.cellcore.code.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * genearal database manager
 */
@Repository
public class GeneralDao {

    /**
     * typicall JPA manager
     */
    @PersistenceContext
    protected EntityManager em;

    public <T extends AbstractJPAEntity> T read(Class<T> class_, long authorId) {
        return em.find(class_, authorId);
    }

    public <T extends AbstractJPAEntity> T save(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T extends AbstractJPAEntity> List<T> list(Class<T> authorClass) {
        return em.createQuery("from " + authorClass.getSimpleName()).getResultList();
    }

    public <T extends AbstractJPAEntity> List<T> read(Class<T> authorClass, String property, Object value) {
        return em.createQuery("select obj from " + authorClass.getSimpleName() + " obj where obj." + property + "=:" + property + "Val").setParameter(property + "Val", value).getResultList();
    }

    public List<Message> readMessages(long id, long authorId, long starting) {
        return em.createQuery("select obj from " + Message.class.getSimpleName() + " obj where ((obj.fromAuthor.id=:fromId and obj.toAuthor.id=:toId) OR (obj.fromAuthor.id=:toId and obj.toAuthor.id=:fromId)) and obj.id >:starting order by obj.sentDate ASC")
                .setParameter("fromId", id)
                .setParameter("toId", authorId)
                .setParameter("starting", starting)
                .getResultList();
    }
}
