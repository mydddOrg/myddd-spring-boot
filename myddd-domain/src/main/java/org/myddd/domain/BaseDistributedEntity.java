package org.myddd.domain;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class BaseDistributedEntity extends BaseEntity{

    @Id
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private int version;
    public BaseDistributedEntity(){
        this.id = getIdGenerate().nextId();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        Preconditions.checkArgument(id > 0,"分布式ID主键值必须大于0，不能为0或为负数");
        this.id = id;
    }

    private static IDGenerate idGenerate;

    private static IDGenerate getIdGenerate(){
        if(Objects.isNull(idGenerate)){
            idGenerate = InstanceFactory.getInstance(IDGenerate.class);
        }
        return idGenerate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseDistributedEntity)) return false;
        BaseDistributedEntity that = (BaseDistributedEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
