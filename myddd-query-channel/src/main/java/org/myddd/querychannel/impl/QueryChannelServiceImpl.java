package org.myddd.querychannel.impl;


import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.querychannel.query.ChannelJpqlQuery;
import org.myddd.querychannel.query.ChannelNamedQuery;
import org.myddd.querychannel.query.ChannelSqlQuery;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import javax.inject.Named;
import java.util.List;

/**
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class QueryChannelServiceImpl implements QueryChannelService {
    
    private EntityRepository repository;

    public QueryChannelServiceImpl(EntityRepository repository) {
        Assert.notNull(repository, "Repository must set!");
        this.repository = repository;
    }

    public EntityRepository getRepository() {
        if(repository == null){
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    @Override
    public ChannelJpqlQuery createJpqlQuery(String jpql) {
        return new ChannelJpqlQuery(getRepository(), jpql);
    }

    @Override
    public <T> ChannelQuery<T> createJpqlQuery(String jpql, Class<T> tClass) {
        return new ChannelJpqlQuery<T>(getRepository(), jpql);
    }

    @Override
    public ChannelNamedQuery createNamedQuery(String queryName) {
        return new ChannelNamedQuery(getRepository(), queryName);
    }

    @Override
    public <T> ChannelQuery<T> createNamedQuery(String queryName, Class<T> tClass) {
        return new ChannelNamedQuery<>(getRepository(), queryName);
    }

    @Override
    @Deprecated
    public ChannelSqlQuery createSqlQuery(String sql) {
        return new ChannelSqlQuery(getRepository(), sql);
    }

    @Override
    public <T> ChannelQuery<T> createSqlQuery(String sql, Class<T> tClass) {
        return new ChannelSqlQuery<>(getRepository(), sql);
    }

    @Override
    public <T> List<T> list(ChannelQuery query) {
        return query.list();
    }

    @Override
    public <T> Page<T> pagedList(ChannelQuery query) {
        return query.pagedList();
    }

    @Override
    public <T> T getSingleResult(ChannelQuery query) {
        return (T) query.singleResult();
    }

    @Override
    public long getResultCount(ChannelQuery query) {
        return query.queryResultCount();
    }
    
}
