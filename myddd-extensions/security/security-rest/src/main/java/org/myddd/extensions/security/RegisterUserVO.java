package org.myddd.extensions.security;

import com.google.common.base.Strings;
import org.myddd.extensions.security.api.UserDto;

public class RegisterUserVO {

    private String email;

    private String phone;

    private String password;

    private String validCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }


    public UserDto toUserDto(){
        UserDto.Builder builder = UserDto.newBuilder();
        if(!Strings.isNullOrEmpty(password))builder.setPassword(password);
        if(!Strings.isNullOrEmpty(email))builder.setEmail(email);
        if(!Strings.isNullOrEmpty(phone))builder.setPhone(phone);
        return builder.build();
    }
}
