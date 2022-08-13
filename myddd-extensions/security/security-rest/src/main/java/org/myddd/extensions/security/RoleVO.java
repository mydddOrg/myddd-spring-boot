package org.myddd.extensions.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Strings;
import org.myddd.extensions.security.api.RoleDto;

import java.util.Objects;

public class RoleVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String roleId;

    private String name;

    private long created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public static RoleVO of(RoleDto dto){
        RoleVO roleVO = new RoleVO();
        roleVO.setId(dto.getId());
        roleVO.setRoleId(dto.getRoleId());
        roleVO.setName(dto.getName());
        roleVO.setCreated(dto.getCreated());
        return roleVO;
    }

    public RoleDto toDto(){
        RoleDto.Builder builder = RoleDto.newBuilder();
        if(Objects.nonNull(id) && id >0)builder.setId(id);
        if(!Strings.isNullOrEmpty(roleId))builder.setRoleId(roleId);
        if(!Strings.isNullOrEmpty(name))builder.setName(name);
        return builder.build();
    }
}
