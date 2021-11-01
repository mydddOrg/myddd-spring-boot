package org.myddd.persistence.jpa;

import org.myddd.domain.*;
import org.myddd.querychannel.BaseQuery;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.basequery.*;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用仓储接口的JPA实现。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class EntityRepositoryJpa extends BaseRepository implements EntityRepository {

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
        T entity = getEntityManager().find(clazz, id);
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
