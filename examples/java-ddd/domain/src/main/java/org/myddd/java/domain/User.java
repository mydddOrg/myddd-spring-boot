package org.myddd.java.domain;

import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name = "user_")
public class User extends BaseDistributedEntity {

    private String userId;

    @Transient
    private String password;

    private String encodePassword;

    private String name;

    private String phone;

    private String email;

    private boolean disabled = false;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    private static UserRepository userRepository;

    private static UserRepository getUserRepository(){
        if(Objects.isNull(userRepository)){
            userRepository = InstanceFactory.getInstance(UserRepository.class);
        }
        return userRepository;
    }

    public User createUser(){
        if(Strings.isNullOrEmpty(userId))throw new RuntimeException("用户ID不能为空");
        if(Strings.isNullOrEmpty(name))throw new RuntimeException("用户名称不能为空");

        return getUserRepository().save(this);
    }
}
