package org.myddd.extensions.security.application.assembler;


import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.domain.Role;

public class RoleAssembler {

    private RoleAssembler(){}

    public static RoleDto toDto(Role role){
        return RoleDto
                .newBuilder()
                .setId(role.getId())
                .setName(role.getName())
                .setRoleId(role.getRoleId())
                .setCreated(role.getCreated())
                .build();
    }

    public static Role toEntity(RoleDto roleDto){
        Role role = new Role();
        if(roleDto.getId() > 0) role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setRoleId(roleDto.getRoleId());
        role.setCreated(roleDto.getCreated());
        return role;
    }
}
