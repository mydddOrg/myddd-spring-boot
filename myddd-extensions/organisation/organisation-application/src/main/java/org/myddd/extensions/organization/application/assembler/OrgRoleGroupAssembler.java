package org.myddd.extensions.organization.application.assembler;


import org.myddd.extensions.organisation.domain.OrgRoleGroup;
import org.myddd.extensions.organization.api.OrgRoleGroupDto;

import java.util.Objects;

public class OrgRoleGroupAssembler {

    public static OrgRoleGroup toEntity(OrgRoleGroupDto dto){
        if(Objects.isNull(dto))return null;
        var orgRoleGroup = new OrgRoleGroup();
        if(dto.getId() > 0) orgRoleGroup.setId(dto.getId());
        orgRoleGroup.setName(dto.getName());
        orgRoleGroup.setCompany(OrganizationAssembler.toCompany(dto.getOrganization()));
        return orgRoleGroup;
    }

    public static OrgRoleGroupDto toDto(OrgRoleGroup entity){
        if(Objects.isNull(entity))return null;
        return OrgRoleGroupDto.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setOrganization(OrganizationAssembler.toDTO(entity.getCompany()))
                .build();
    }
}
