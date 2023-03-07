package org.myddd.persistence.jpa;

import org.myddd.domain.InstanceFactory;

import jakarta.persistence.EntityManager;

public abstract class BaseRepository {

    //命名查询解析器，它是可选的
    private NamedQueryParser namedQueryParser;

    private EntityManagerProvider entityManagerProvider;

    public EntityManagerProvider getEntityManagerProvider() {
        if(entityManagerProvider == null){
            entityManagerProvider = InstanceFactory.getInstance(EntityManagerProvider.class);
        }
        return entityManagerProvider;
    }

    protected NamedQueryParser getNamedQueryParser() {
        if (namedQueryParser == null) {
            namedQueryParser = InstanceFactory.getInstance(NamedQueryParser.class);
        }
        return namedQueryParser;
    }

    public EntityManager getEntityManager() {
        return getEntityManagerProvider().getEntityManager();
    }

}
