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
public class EntityRepositoryJpa implements EntityRepository, QueryRepository {

    //命名查询解析器，它是可选的
    private NamedQueryParser namedQueryParser;

    private EntityManagerProvider entityManagerProvider;

    public EntityManagerProvider getEntityManagerProvider() {
        if(entityManagerProvider == null){
            entityManagerProvider = InstanceFactory.getInstance(EntityManagerProvider.class);
        }
        return entityManagerProvider;
    }

    private NamedQueryParser getNamedQueryParser() {
        if (namedQueryParser == null) {
            namedQueryParser = InstanceFactory.getInstance(NamedQueryParser.class);
        }
        namedQueryParser.setEntityManagerProvider(getEntityManagerProvider());
        return namedQueryParser;
    }

    public final void setNamedQueryParser(NamedQueryParser namedQueryParser) {
        namedQueryParser.setEntityManagerProvider(entityManagerProvider);
		this.namedQueryParser = namedQueryParser;
	}

	public EntityManager getEntityManager() {
        return getEntityManagerProvider().getEntityManager();
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


    @Override
    public <T> List<T> find(BaseQuery<T> baseQuery) {
        Query query = getQuery(baseQuery);
        processQuery(query,baseQuery);
        return query.getResultList();
    }

    @Override
    public <T> T getSingleResult(BaseQuery<T> baseQuery) {
        return find(baseQuery).stream().findFirst().orElse(null);
    }


    private <T> Query getQuery(BaseQuery<T> baseQuery){
        Query query = null;
        switch (baseQuery.queryType()){
            case JPQL_QUERY: {
                query = getEntityManager().createQuery(baseQuery.queryName());
                break;
            }
            case NAMED_QUERY: {
                query = getEntityManager().createNamedQuery(baseQuery.queryName());
                break;
            }
            case SQL_QUERY: {
                query = getEntityManager().createNativeQuery(baseQuery.queryName());
                break;
            }
            default: {
                break;
            }
        }
        return query;
    }

    @Override
    public String getQueryStringOfNamedQuery(String queryName) {
        return getNamedQueryParser().getQueryStringOfNamedQuery(queryName);
    }

    private void processQuery(Query query, BaseQuery<?> originQuery) {
        processQuery(query, originQuery.getParameters(),
                originQuery.getFirstResult(), originQuery.getMaxResults());
    }

    private void processQuery(Query query, QueryParameters parameters,
            int firstResult, int maxResults) {
        fillParameters(query, parameters);
        query.setFirstResult(firstResult);
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }
    }

    private void fillParameters(Query query, QueryParameters params) {
        if (params == null) {
            return;
        }
        if (params instanceof PositionalParameters) {
            fillParameters(query, (PositionalParameters) params);
        } else if (params instanceof NamedParameters) {
            fillParameters(query, (NamedParameters) params);
        } else {
            throw new UnsupportedOperationException("不支持的参数形式");
        }
    }

    private void fillParameters(Query query, PositionalParameters params) {
        Object[] paramArray = params.getParams();
        for (int i = 0; i < paramArray.length; i++) {
            query = query.setParameter(i + 1, paramArray[i]);
        }
    }

    private void fillParameters(Query query, NamedParameters params) {
        for (Map.Entry<String, Object> each : params.getParams().entrySet()) {
            query = query.setParameter(each.getKey(), each.getValue());
        }
    }
}
