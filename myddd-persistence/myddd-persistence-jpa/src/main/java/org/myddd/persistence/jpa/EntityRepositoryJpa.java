package org.myddd.persistence.jpa;

import org.myddd.domain.Entity;
import org.myddd.domain.EntityRepository;

import jakarta.inject.Named;
import java.io.Serializable;

/**
 * 通用仓储接口的JPA实现。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class EntityRepositoryJpa extends BaseRepository implements EntityRepository {

    @Override
    public <T extends Entity> T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public <T extends Entity> T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public <T extends Entity> T save(T entity) {
        if (entity.notExisted()) {
            getEntityManager().persist(entity);
            return entity;
        }
        return getEntityManager().merge(entity);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.myddd.domain.EntityRepository#remove(org.myddd.domain.Entity)
     */
    @Override
    public void remove(Entity entity) {
        getEntityManager().remove(get(entity.getClass(), entity.getId()));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.myddd.domain.EntityRepository#exists(java.io.Serializable)
     */
    @Override
    public <T extends Entity> boolean exists(final Class<T> clazz,
                                             final Serializable id) {
        var entity = getEntityManager().find(clazz, id);
        return entity != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.myddd.domain.EntityRepository#get(java.io.Serializable)
     */
    @Override
    public <T extends Entity> T get(final Class<T> clazz, final Serializable id) {
        return getEntityManager().find(clazz, id);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.myddd.domain.EntityRepository#load(java.io.Serializable)
     */
    @Override
    public <T extends Entity> T load(final Class<T> clazz, final Serializable id) {
        return getEntityManager().getReference(clazz, id);
    }

}
