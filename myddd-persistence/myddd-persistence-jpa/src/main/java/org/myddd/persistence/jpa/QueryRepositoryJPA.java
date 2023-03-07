package org.myddd.persistence.jpa;

import org.myddd.querychannel.BaseQuery;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.basequery.QueryParameters;

import jakarta.inject.Named;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Named
public class QueryRepositoryJPA extends BaseRepository implements QueryRepository {

    @Override
    public <T> List<T> find(BaseQuery<T> baseQuery) {
        var query = getQuery(baseQuery);
        processQuery(query,baseQuery);
        return query.getResultList();
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
    public <T> T getSingleResult(BaseQuery<T> baseQuery) {
        return find(baseQuery).stream().findFirst().orElse(null);
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
        if (params instanceof BaseQuery.PositionalParameters) {
            fillParameters(query, (BaseQuery.PositionalParameters) params);
        } else if (params instanceof BaseQuery.NamedParameters) {
            fillParameters(query, (BaseQuery.NamedParameters) params);
        } else {
            throw new UnsupportedOperationException("不支持的参数形式");
        }
    }

    private void fillParameters(Query query, BaseQuery.PositionalParameters params) {
        Object[] paramArray = params.getParams();
        for (var i = 0; i < paramArray.length; i++) {
            query = query.setParameter(i + 1, paramArray[i]);
        }
    }

    private void fillParameters(Query query, BaseQuery.NamedParameters params) {
        for (Map.Entry<String, Object> each : params.getParams().entrySet()) {
            query = query.setParameter(each.getKey(), each.getValue());
        }
    }

}
