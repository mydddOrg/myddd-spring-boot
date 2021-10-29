package org.myddd.domain.mock;

import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "user_",
        indexes = {
                @Index(name = "index_user_id", columnList = "user_id")
        })
public class User extends BaseDistributedEntity {

    @Column(name = "user_id",nullable = false)
    private String userId;

    @Column(name = "user_type")
    private UserType userType;

    @Transient
    private String password;

    @Column(name = "encode_password",nullable = false)
    private String encodePassword;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String email;

    private boolean disabled;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    private static UserRepository userRepository;

    private static UserRepository getUserRepository(){
        if(Objects.isNull(userRepository)){
            userRepository = InstanceFactory.getInstance(UserRepository.class);
        }
        return userRepository;
    }

    public User createLocalUser(){
        if(Strings.isNullOrEmpty(name))throw new UserNameEmptyException();
        if(Strings.isNullOrEmpty(password))throw new PasswordEmptyException();
        if(Strings.isNullOrEmpty(userId))throw new UserIdEmptyException();

        this.created = System.currentTimeMillis();
        this.encodePassword = password;
        return getUserRepository().save(this);
    }

    public static User queryUserByUserId(String userId){
        return getUserRepository().queryUserByUserId(userId);
    }


    public void enable(){
        enableOrDisable(false);
    }

    public void disable(){
        enableOrDisable(true);
    }

    private void enableOrDisable(boolean disabled){
        if(Strings.isNullOrEmpty(userId)){
            throw new UserIdEmptyException();
        }
        User exists = queryUserByUserId(userId);
        if(Objects.isNull(exists)){
            throw new UserNotFoundException(userId);
        }

        exists.disabled = disabled;
        getUserRepository().save(exists);
    }


}
