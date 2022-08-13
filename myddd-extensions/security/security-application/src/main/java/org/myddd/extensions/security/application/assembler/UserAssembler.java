package org.myddd.extensions.security.application.assembler;

import org.myddd.extensions.security.api.OptionalUserDto;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.extensions.security.domain.User;

import java.util.Objects;

public class UserAssembler {

    private UserAssembler(){}

    public static User toUser(UserDto dto){
        if(Objects.isNull(dto))return null;
        User user = new User();
        if(dto.getId() > 0)user.setId(dto.getId());
        user.setUserId(dto.getUserId());
        user.setPassword(dto.getPassword());
        user.setEncodePassword(dto.getEncodePassword());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setDisabled(dto.getDisabled());
        return user;
    }

    public static OptionalUserDto toOptionalUserDto(User user){
        if(Objects.isNull(user))return OptionalUserDto.getDefaultInstance();
        return OptionalUserDto.newBuilder()
                .setUser(toUserDto(user))
                .build();
    }
    public static UserDto toUserDto(User user){
        if(Objects.isNull(user))return null;
        UserDto.Builder builder =  UserDto.newBuilder()
                .setId(user.getId())
                .setUserId(user.getUserId())
                .setEncodePassword(user.getEncodePassword())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .setDisabled(user.isDisabled());

        if(Objects.nonNull(user.getPassword())){
            builder.setPassword(user.getPassword());
        }

        return builder.build();
    }

}
