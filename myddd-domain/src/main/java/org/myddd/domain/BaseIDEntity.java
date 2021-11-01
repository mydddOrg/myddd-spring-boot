package org.myddd.domain;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseIDEntity implements Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean existed() {
        if (id == null) {
            return false;
        }
        return getRepository().exists(getClass(), getId());
    }

    @Override
    public boolean notExisted() {
        return !existed();
    }

    private static EntityRepository repository;

    protected static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
