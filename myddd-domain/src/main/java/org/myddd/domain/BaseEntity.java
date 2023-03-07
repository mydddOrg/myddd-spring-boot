package org.myddd.domain;

import jakarta.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 抽象实体类，可作为所有领域实体的基类。
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Entity {

    private static final long serialVersionUID = 8882145540383345037L;

    /**
     * 判断该实体是否已经存在于数据库中。
     * @return 如果数据库中已经存在拥有该id的实体则返回true，否则返回false。
     */
    @Override
    public boolean existed() {
        Object id = getId();
        if (id == null) {
            return false;
        }
        return getRepository().exists(getClass(), getId());
    }

    /**
     * 判断该实体是否不存在于数据库中。
     * @return 如果数据库中已经存在拥有该id的实体则返回false，否则返回true。
     */
    @Override
    public boolean notExisted() {
        return !existed();
    }

    private static EntityRepository repository;

    /**
     * 获取仓储对象实例。如果尚未拥有仓储实例则通过InstanceFactory向IoC容器获取一个。
     * @return 仓储对象实例
     */
    private static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
