package org.myddd.security.account.application.assembler;

import org.myddd.security.account.domain.LoginEntity;
import org.myddd.security.api.LoginDTO;

import javax.inject.Named;
import java.util.Objects;

@Named
public class LoginAssembler {

    public LoginEntity toEntity(LoginDTO dto){
        if(Objects.isNull(dto))return null;
        LoginEntity entity = new LoginEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setDisplayName(dto.getDisplayName());
        entity.setDisabled(dto.isDisabled());
        entity.setCreateDate(dto.getCreated());
        entity.setUpdateDate(dto.getUpdated());
        entity.setSuperUser(dto.isSuperUser());
        entity.setId(dto.getId());
        return entity;
    }

    public LoginDTO toDTOWithoutPassword(LoginEntity entity){
        LoginDTO dto = toDTO(entity);
        dto.setPassword("");
        dto.setNewPassword("");
        return dto;
    }

    public LoginDTO toDTO(LoginEntity entity){
        if(Objects.isNull(entity))return null;
        LoginDTO dto = new LoginDTO();
        dto.setUpdated(entity.getUpdateDate());
        dto.setCreated(entity.getCreateDate());
        dto.setUsername(entity.getUsername());
        dto.setDisplayName(entity.getDisplayName());
        dto.setPassword(entity.getPassword());
        dto.setDisabled(entity.isDisabled());
        dto.setSuperUser(entity.isSuperUser());
        dto.setId(entity.getId());
        return dto;
    }
}
