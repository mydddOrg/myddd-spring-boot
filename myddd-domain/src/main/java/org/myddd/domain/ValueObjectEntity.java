package org.myddd.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.myddd.utils.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class ValueObjectEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id"
    )
    private Long id;

    protected Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private static EntityRepository repository;

    /**
     * 获取仓储对象实例。如果尚未拥有仓储实例则通过InstanceFactory向IoC容器获取一个。
     * @return 仓储对象实例
     */
    protected static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    /**
     * 设置仓储实例。该方法主要用于单元测试。产品系统中通常是通过IoC容器获取仓储实例。
     * @param repository 要设置的仓储对象实例
     */
    public static void setRepository(EntityRepository repository) {
        ValueObjectEntity.repository = repository;
    }

    /**
     * 获取业务主键。业务主键是判断相同类型的两个实体在业务上的等价性的依据。如果相同类型的两个
     * 实体的业务主键相同，则认为两个实体是相同的，代表同一个实体。
     * <p>业务主键由实体的一个或多个属性组成。
     * @return 组成业务主键的属性的数组。
     */
    public String[] businessKeys() {
        return new String[] {"id"};
    }

    /**
     * 依据业务主键获取哈希值。用于判定两个实体是否等价。
     * 等价的两个实体的hashCode相同，不等价的两个实体hashCode不同。
     * @return 实体的哈希值
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(13, 37);
        if (businessKeys() == null || businessKeys().length == 0) {
            return builder.append(getId()).toHashCode();
        }
        BeanUtils thisBeanUtils = new BeanUtils(this);
        for (String businessKey : businessKeys()) {
            builder = builder.append(thisBeanUtils.getPropValue(businessKey));
        }
        return builder.toHashCode();
    }

    /**
     * 依据业务主键判断两个实体是否等价。
     * @param other 另一个实体
     * @return 如果本实体和other等价则返回true,否则返回false
     */
    @Override
    public boolean equals(Object other) {
        return EntityEqualsBuilder.isEquals(this, other);
    }

    private static class EntityEqualsBuilder {
        public static boolean isEquals(ValueObjectEntity entity, Object other) {
            if (entity == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            if (!(entity.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            }
            BeanUtils thisBeanUtils = new BeanUtils(entity);
            BeanUtils thatBeanUtils = new BeanUtils(other);
            EqualsBuilder builder = new EqualsBuilder();
            if (entity.businessKeys() == null || entity.businessKeys().length == 0) {
                return builder.append(entity.getId(), ((BaseEntity)other).getId()).isEquals();
            }
            for (String businessKey : entity.businessKeys()) {
                builder.append(thisBeanUtils.getPropValue(businessKey), thatBeanUtils.getPropValue(businessKey));
            }
            return builder.isEquals();
        }
    }

}
