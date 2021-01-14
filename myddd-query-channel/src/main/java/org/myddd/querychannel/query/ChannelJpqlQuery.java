package org.myddd.querychannel.query;


import org.myddd.domain.BaseQuery;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.JpqlQuery;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import java.util.List;

/**
 * 通道查询的JPQL实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class ChannelJpqlQuery extends ChannelQuery<ChannelJpqlQuery> {
    
    private final JpqlQuery query;

    public ChannelJpqlQuery(EntityRepository repository, String jpql) {
        super(repository);
        Assert.notBlank(jpql, "JPQL must be set!");
        query = new JpqlQuery(repository, jpql);
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
        return query.getJpql();
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return repository.createJpqlQuery(queryString);
    }

}
