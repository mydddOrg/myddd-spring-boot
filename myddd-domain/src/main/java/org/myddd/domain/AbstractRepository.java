package org.myddd.domain;

import java.io.Serializable;
import java.util.Collection;

public interface AbstractRepository {

    <T extends Entity> T save(T entity);

    <T extends Entity> T get(Class<T> clazz, Serializable id);

    <T extends Entity> boolean exists(Class<T> clazz, Serializable id);

    void remove(Entity entity);

    <T extends Entity> void batchSaveEntities(Collection<T> entities);

}
