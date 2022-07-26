package org.myddd.java.application.assembler;

import org.myddd.java.api.dto.UserDTO;
import org.myddd.java.domain.User;

import java.util.Objects;

public class UserAssembler {

    public static UserDTO toUserDTO(User user){
        if(Objects.isNull(user))return null;
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO){
        if(Objects.isNull(userDTO))return null;
        var user = new User();
        if(Objects.nonNull(userDTO.getId()))user.setId(userDTO.getId());
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        return user;
    }
}
