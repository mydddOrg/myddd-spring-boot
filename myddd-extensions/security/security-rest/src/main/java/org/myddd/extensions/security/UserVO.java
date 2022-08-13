package org.myddd.extensions.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Strings;
import org.myddd.extensions.security.api.UserDto;

import java.util.Objects;

public class UserVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String phone;

    private String email;

    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public static UserVO of(UserDto userDto){
        UserVO userVO = new UserVO();
        userVO.setId(userDto.getId());
        userVO.setName(userDto.getName());
        userVO.setUserId(userDto.getUserId());
        userVO.setEmail(userDto.getEmail());
        userVO.setPhone(userDto.getPhone());
        userVO.setDisabled(userDto.getDisabled());
        return userVO;
    }


    public UserDto toDto(){
        UserDto.Builder builder = UserDto.newBuilder();
        if(Objects.nonNull(id))builder.setId(id);
        if(!Strings.isNullOrEmpty(userId))builder.setUserId(userId);
        if(!Strings.isNullOrEmpty(name))builder.setName(name);
        if(!Strings.isNullOrEmpty(password))builder.setPassword(password);
        if(!Strings.isNullOrEmpty(email))builder.setEmail(email);
        if(!Strings.isNullOrEmpty(phone))builder.setPhone(phone);
        builder.setDisabled(disabled);
        return builder.build();
    }


}
