package org.myddd.querychannel.query;



import org.myddd.domain.BaseQuery;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.NamedQuery;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import java.util.List;

/**
 * 通道查询的SQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelNamedQuery extends ChannelQuery<ChannelNamedQuery> {
    
    private NamedQuery query;

    public ChannelNamedQuery(EntityRepository repository, String queryName) {
        super(repository);
        Assert.notBlank(queryName, "Query name must be set!");
        query = new NamedQuery(repository, queryName);
        setQuery(query);
    }

    @Override
    public <T> List<T> list() {
        return query.list();
    }

    @Override
    public <T> Page<T> pagedList() {
        return new Page<T>(query.getFirstResult(), queryResultCount(), 
                query.getMaxResults(), (List<T>) query.list());
    }

    @Override
    public <T> T singleResult() {
        return (T) query.singleResult();
    }

    @Override
    protected String getQueryString() {
        return repository.getQueryStringOfNamedQuery(query.getQueryName());
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return repository.createJpqlQuery(queryString);
    }

}
