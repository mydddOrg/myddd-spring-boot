package org.myddd.domain;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 仓储访问接口。用于存取和查询数据库中的各种类型的实体。
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 *
 */
public interface EntityRepository {


    EntityManager getEntityManager();


    /**
     * 新增一个实体
     * @param entity 需要新增的实体
     * @param <T> 要新增的实体类型
     * @return 新增后的实体
     */
    <T extends Entity> T create(T entity);

    /**
     * 更新一个实体
     * @param entity 需要更新的实体
     * @param <T> 更新的实体类型
     * @return
     */
    <T extends Entity> T update(T entity);

    /**
     * 将实体（无论是新的还是修改了的）保存到仓储中。
     *
     * @param <T> 实体的类型
     * @param entity 要存储的实体实例。
     * @return 持久化后的当前实体
     */
    <T extends Entity> T save(T entity);

    /**
     * 将实体从仓储中删除。如果仓储中不存在此实例将抛出EntityNotExistedException异常。
     *
     * @param entity 要删除的实体实例。
     */
    void remove(Entity entity);

    /**
     * 判断仓储中是否存在指定ID的实体实例。
     *
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param id 实体标识
     * @return 如果实体实例存在，返回true，否则返回false
     */
    <T extends Entity> boolean exists(Class<T> clazz, Serializable id);

    /**
     * 从仓储中获取指定类型、指定ID的实体
     *
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param id 实体标识
     * @return 一个实体实例。
     */
    <T extends Entity> T get(Class<T> clazz, Serializable id);

    /**
     * 从仓储中装载指定类型、指定ID的实体
     *
     * @param <T> 实体类型
     * @param clazz 实体的类
     * @param id 实体标识
     * @return 一个实体实例。
     */
    <T extends Entity> T load(Class<T> clazz, Serializable id);

}
