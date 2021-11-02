package org.myddd.security.account.domain;


import com.google.common.base.Preconditions;
import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(columnList = "username",unique = true)
})
public class LoginEntity extends BaseIDEntity {

    @Column(unique = true)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    private String password;

    private boolean disabled;

    private boolean superUser;

    private long createDate;

    private long updateDate;

    public LoginEntity(){
        this.createDate = System.currentTimeMillis();
        this.updateDate = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    private static LoginRepository repository;

    private static LoginRepository getLoginRepository(){
        if(Objects.isNull(repository)){
            repository = InstanceFactory.getInstance(LoginRepository.class);
        }
        return repository;
    }

    public static boolean isEmpty(){
        return getLoginRepository().isEmpty();
    }

    public LoginEntity createUser(){
        this.superUser = false;
        return getLoginRepository().createLoginUser(this);
    }

    public LoginEntity createSuperUser(){
        this.superUser = true;
        return getLoginRepository().createLoginUser(this);
    }

    public LoginEntity updateUser(){
        LoginEntity queryLogin = getLoginRepository().findByUsername(this.username);

        Preconditions.checkNotNull(queryLogin,"找不到当前用户");
        queryLogin.setDisplayName(this.displayName);
        return getLoginRepository().updateUser(queryLogin);
    }

    public static LoginEntity findByUsername(String username){
        return getLoginRepository().findByUsername(username);
    }

    public static boolean exists(String username){
        return getLoginRepository().userExists(username);
    }

    public void enabled(){
        getLoginRepository().enable(this);
    }

    public void disabled(){
        getLoginRepository().disable(this);
    }


    public static void delete(String username){
        getLoginRepository().deleteUser(username);
    }

    public static boolean updatePassword(String username,String password,String newPassword){
        LoginEntity loginEntity = LoginEntity.findByUsername(username);
        Preconditions.checkNotNull(loginEntity,"找不到用户");

        boolean check =  password.equals(loginEntity.password);
        Preconditions.checkState(check,"原密码错误");

        loginEntity.setPassword(newPassword);

        getLoginRepository().updateUser(loginEntity);
        return true;
    }

    public static boolean updatePasswordByAdmin(String username,String newPassword){
        LoginEntity loginEntity = LoginEntity.findByUsername(username);
        Preconditions.checkNotNull(loginEntity,"找不到用户");
        loginEntity.setPassword(newPassword);
        getLoginRepository().updateUser(loginEntity);
        return true;
    }

}
