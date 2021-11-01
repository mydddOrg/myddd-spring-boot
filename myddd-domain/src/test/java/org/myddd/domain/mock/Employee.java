package org.myddd.domain.mock;

import org.myddd.domain.BaseIDEntity;

import javax.persistence.Entity;

@Entity
public class Employee extends BaseIDEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee createEmployee(){
        return getRepository().save(this);
    }

}
