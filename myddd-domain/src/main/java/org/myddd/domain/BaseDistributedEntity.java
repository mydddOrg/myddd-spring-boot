package org.myddd.domain;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
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

}
