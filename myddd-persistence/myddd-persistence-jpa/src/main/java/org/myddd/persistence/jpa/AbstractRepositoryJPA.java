package org.myddd.persistence.jpa;

import org.myddd.domain.AbstractRepository;
import org.myddd.domain.Entity;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Objects;

/**
 * JAP仓储实现的抽象父类
 */
public abstract class AbstractRepositoryJPA implements AbstractRepository {

    private static EntityRepository entityRepository;

    private static EntityRepository getEntityRepository() {
        if(Objects.isNull(entityRepository)){
            entityRepository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return entityRepository;
    }

    protected EntityManager getEntityManager(){
        return getEntityRepository().getEntityManager();
    }

    public <T extends Entity> T save(T entity){
        return getEntityRepository().save(entity);
    }

    public <T extends Entity> T get(Class<T> clazz, Serializable id){
        return getEntityRepository().get(clazz,id);
    }

    public <T extends Entity> boolean exists(Class<T> clazz, Serializable id){
        return getEntityRepository().exists(clazz,id);
    }

    public void remove(Entity entity){
        getEntityManager().remove(entity);
    }

}
