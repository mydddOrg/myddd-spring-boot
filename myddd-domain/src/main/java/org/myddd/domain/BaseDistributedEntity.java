package org.myddd.domain;

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

    public BaseDistributedEntity(){
        this.id = getIdGenerate().nextId();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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
}
