package org.myddd.querychannel.impl;


import com.google.common.base.Preconditions;
import org.myddd.domain.InstanceFactory;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.querychannel.QueryRepository;
import org.myddd.querychannel.query.ChannelJpqlQuery;
import org.myddd.querychannel.query.ChannelNamedQuery;
import org.myddd.querychannel.query.ChannelSqlQuery;

import jakarta.inject.Named;

/**
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class QueryChannelServiceImpl implements QueryChannelService {
    
    private QueryRepository repository;

    public QueryChannelServiceImpl(QueryRepository repository) {
        Preconditions.checkNotNull(repository, "Repository must set!");
        this.repository = repository;
    }

    public QueryRepository getRepository() {
        if(repository == null){
            repository = InstanceFactory.getInstance(QueryRepository.class);
        }
        return repository;
    }

    @Override
    public <T> ChannelQuery<T> createJpqlQuery(String jpql, Class<T> tClass) {
        return new ChannelJpqlQuery<>(getRepository(), jpql);
    }

    @Override
    public <T> ChannelQuery<T> createNamedQuery(String queryName, Class<T> tClass) {
        return new ChannelNamedQuery<>(getRepository(), queryName);
    }

    @Override
    public <T> ChannelQuery<T> createSqlQuery(String sql, Class<T> tClass) {
        return new ChannelSqlQuery<>(getRepository(), sql);
    }
    
}
