package org.myddd.domain.mock;

import org.myddd.domain.BaseEntity;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Organization extends BaseEntity {

    @Id
    private String id;

    private String name;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static EntityRepository repository;

    private static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    public Organization createOrg(){
        return getRepository().save(this);
    }

    public Organization queryById(String id){
        return getRepository().get(Organization.class,id);
    }
}
