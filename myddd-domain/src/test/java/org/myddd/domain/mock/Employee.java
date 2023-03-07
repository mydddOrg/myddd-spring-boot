package org.myddd.domain.mock;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;

import jakarta.persistence.Entity;

@Entity
public class Employee extends BaseIDEntity {

    private String name;

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

    public Employee createEmployee(){
        return getRepository().save(this);
    }

}
