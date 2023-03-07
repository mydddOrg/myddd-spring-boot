package org.myddd.persistence.jpa;


import org.myddd.domain.InstanceFactory;
import org.myddd.domain.IocInstanceNotFoundException;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * JPA 实体管理器提供者。如果当前线程中尚未存在entityManager线程变量，则从IoC容器中获取一个并存入当前线程，
 * 如果当前线程已经存在entityManager线程变量，直接返回。
 * <p>
 * 本类的存在，主要是为了在当前线程中，每次请求都返回相同的entityManager对象。避免事务和“会话已关闭”问题。
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class EntityManagerProvider {

    private EntityManagerFactory entityManagerFactory;

    private final ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<>();

    public EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null){
            entityManagerFactory = InstanceFactory.getInstance(EntityManagerFactory.class);
        }
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        var result = entityManagerHolder.get();
        if (result != null && result.isOpen()) {
            return result;
        }
        result = getEntityManagerFromIoC();
        entityManagerHolder.set(result);
        return result;
    }

    public void cleanup() {
        entityManagerHolder.remove();
    }

    public EntityManager getEntityManagerFromIoC() {
        try {
            return InstanceFactory.getInstance(EntityManager.class);
        } catch (IocInstanceNotFoundException e) {
            if (entityManagerFactory == null) {
                entityManagerFactory = InstanceFactory.getInstance(EntityManagerFactory.class);
            }
            return entityManagerFactory.createEntityManager();
        }
    }
}
