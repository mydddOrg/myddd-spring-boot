package org.myddd.querychannel.query;



import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.basequery.BaseQuery;
import org.myddd.querychannel.basequery.NamedQuery;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import java.util.List;

/**
 * 通道查询的SQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelNamedQuery<T> extends ChannelQuery<T> {
    
    private NamedQuery query;

    public ChannelNamedQuery(QueryRepository repository, String queryName) {
        super(repository);
        Assert.notBlank(queryName, "Query name must be set!");
        query = new NamedQuery(queryName);
        setQuery(query);
    }

    @Override
    public  List<T> list() {
        return query.list();
    }

    @Override
    public Page<T> pagedList() {
        return new Page<T>(query.getFirstResult(), queryResultCount(), 
                query.getMaxResults(), (List<T>) query.list());
    }

    @Override
    public T singleResult() {
        return (T) query.singleResult();
    }

    @Override
    protected String getQueryString() {
        return repository.getQueryStringOfNamedQuery(query.getQueryName());
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return new NamedQuery(queryString);
    }

}
