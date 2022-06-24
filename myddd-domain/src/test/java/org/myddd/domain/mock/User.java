package org.myddd.domain.mock;

import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.EntityRepository;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_")
public class User extends BaseDistributedEntity {

    @Column(name = "user_id",nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public User createUser(){
        return getRepository().save(this);
    }

    public static User queryByUserId(Long userId){
        return getRepository().get(User.class,userId);
    }

    public User updateUser(){
        User exists = User.queryByUserId(getId());
        if(Objects.isNull(exists)){
            throw new RuntimeException(String.format("User %s not exists",getId()));
        }
        return getRepository().save(exists);
    }
}
