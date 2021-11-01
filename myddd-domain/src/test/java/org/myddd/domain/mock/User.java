package org.myddd.domain.mock;

import org.myddd.domain.BaseDistributedEntity;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
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

    public User createUser(){
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
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
        exists.updated = System.currentTimeMillis();
        return getRepository().save(exists);
    }
}
