package org.myddd.domain;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class BaseDistributedEntity implements Entity{

    @Id
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    protected long created;

    protected long updated;

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

    private static EntityRepository repository;

    private static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    private static IDGenerate idGenerate;

    private static IDGenerate getIdGenerate(){
        if(Objects.isNull(idGenerate)){
            idGenerate = InstanceFactory.getInstance(IDGenerate.class);
        }
        return idGenerate;
    }

    @Override
    public boolean existed() {
        return getRepository().exists(this.getClass(),getId());
    }

    @Override
    public boolean notExisted() {
        return !existed();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

}
