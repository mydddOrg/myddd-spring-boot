package org.myddd.querychannel.impl;


import com.google.common.base.Preconditions;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;
import org.myddd.querychannel.ChannelQuery;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.querychannel.query.ChannelJpqlQuery;
import org.myddd.querychannel.query.ChannelNamedQuery;
import org.myddd.querychannel.query.ChannelSqlQuery;
import javax.inject.Named;

/**
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class QueryChannelServiceImpl implements QueryChannelService {
    
    private EntityRepository repository;

    public QueryChannelServiceImpl(EntityRepository repository) {
        Preconditions.checkNotNull(repository, "Repository must set!");
        this.repository = repository;
    }

    public EntityRepository getRepository() {
        if(repository == null){
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    @Override
    public <T> ChannelQuery<T> createJpqlQuery(String jpql, Class<T> tClass) {
        return new ChannelJpqlQuery<T>(getRepository(), jpql);
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
