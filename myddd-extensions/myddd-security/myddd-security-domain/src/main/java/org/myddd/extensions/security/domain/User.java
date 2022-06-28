package org.myddd.extensions.security.domain;

import com.google.common.base.Strings;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_",
        indexes = {
                @Index(name = "index_user_id", columnList = "user_id_")
        },
uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_userid_and_user_type",columnNames = {"user_id_","user_type_"})
})
public class User extends BaseDistributedEntity {

    private static final String PHONE_REGEX = "^\\d{11}$";

    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";

    private static final String DEFAULT_PASSWORD = "123456";

    @Column(name = "user_id_",nullable = false)
    private String userId;

    @Column(name = "user_type_")
    private UserType userType = UserType.LOCAL;

    @Transient
    private String password;

    @Column(name = "encode_password_",nullable = false)
    private String encodePassword;

    @Column(name = "name_",nullable = false)
    private String name;

    @Column(name = "phone_")
    private String phone;

    @Column(name = "email_")
    private String email;

    @Column(name = "disabled_")
    private boolean disabled;

    @Column(name = "created_")
    private long created;

    @Column(name = "updated_")
    private long updated;

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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
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

    private static UserPasswordEncoder passwordEncoder;

    private static UserPasswordEncoder getPasswordEncoder(){
        if(Objects.isNull(passwordEncoder)){
            passwordEncoder = InstanceFactory.getInstance(UserPasswordEncoder.class);
        }
        return passwordEncoder;
    }

    public User createLocalUser(){
        if(Strings.isNullOrEmpty(name))throw new UserNameEmptyException();
        if(Strings.isNullOrEmpty(userId))throw new UserIdEmptyException();

        checkEmailAndPhoneValid(UserType.LOCAL);

        if(Strings.isNullOrEmpty(password))this.password = DEFAULT_PASSWORD;

        this.userType = UserType.LOCAL;
        this.created = System.currentTimeMillis();
        this.encodePassword = getPasswordEncoder().encodePassword(password);
        return getUserRepository().save(this);
    }

    public User registerUser(){
        this.name = Strings.isNullOrEmpty(phone)?email:phone;
        this.userId = Strings.isNullOrEmpty(phone)?email:phone;

        return createLocalUser();
    }

    private void checkEmailAndPhoneValid(UserType userType) {
        if(Strings.isNullOrEmpty(email) && Strings.isNullOrEmpty(phone)) throw new EmailOrPhoneMustHaveOneException();
        checkIsEmailValid(userType);
        checkIsPhoneValid(userType);
    }

    private void checkIsPhoneValid(UserType userType) {
        if(!Strings.isNullOrEmpty(phone)){
            if(!patternMatches(phone,PHONE_REGEX)) throw new IllegalPhoneFormatException(phone);
            var phoneExists = getUserRepository().queryUserByPhone(phone,userType);
            if(Objects.nonNull(phoneExists))throw new PhoneExistsException(phone);
        }
    }

    private void checkIsEmailValid(UserType userType) {
        if(!Strings.isNullOrEmpty(email)){
            if(!patternMatches(email,EMAIL_REGEX)) throw new IllegalEmailFormatException(email);
            var emailExists = getUserRepository().queryUserByEmail(email,userType);
            if(Objects.nonNull(emailExists)) throw new EmailExistsException(email);
        }
    }

    public static User queryLocalUserById(String userId){
        return getUserRepository().queryLocalUserByUserId(userId);
    }

    public static User queryLocalByEmailOrPhone(String emailOrPhone){
        if(patternMatches(emailOrPhone,EMAIL_REGEX)){
            return getUserRepository().queryUserByEmail(emailOrPhone,UserType.LOCAL);
        }

        if(patternMatches(emailOrPhone,PHONE_REGEX)){
            return getUserRepository().queryUserByPhone(emailOrPhone,UserType.LOCAL);
        }

        return null;
    }

    public static User queryUserById(Long id){
        return getUserRepository().get(User.class,id);
    }

    public static User queryById(Long id){
        return getUserRepository().get(User.class,id);
    }

    public void enable(){
        enableOrDisable(false);
    }

    public void disable(){
        enableOrDisable(true);
    }

    private void enableOrDisable(boolean disabled){
        User exists = queryById(getId());
        if(Objects.isNull(exists)){
            throw new UserNotFoundException(getId());
        }

        exists.disabled = disabled;
        getUserRepository().save(exists);
    }

    public static void batchInsertUsers(List<User> userList, UserType userType){
        var existUserIds = getUserRepository().queryExistsUserIdByType(userType);
        var waitInsertUsers = userList.stream().filter(it -> !existUserIds.contains(it.userId)).collect(Collectors.toList());
        waitInsertUsers.forEach(it -> {
            it.userType = userType;
            it.created = System.currentTimeMillis();
            it.encodePassword = getPasswordEncoder().encodePassword(it.password);
        });
        getUserRepository().batchSaveEntities(waitInsertUsers);
    }

    private static boolean patternMatches(String matchString, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(matchString)
                .matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, userType);
    }
}
