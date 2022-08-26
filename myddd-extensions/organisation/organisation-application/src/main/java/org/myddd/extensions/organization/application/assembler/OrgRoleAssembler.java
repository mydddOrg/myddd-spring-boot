package org.myddd.extensions.organization.application.assembler;

import org.myddd.extensions.organisation.domain.OrgRole;
import org.myddd.extensions.organisation.domain.OrgRoleGroup;
import org.myddd.extensions.organization.api.OrgRoleDto;
import org.myddd.extensions.organization.api.OrgRoleGroupDto;

public class OrgRoleAssembler {

    private OrgRoleAssembler(){}

    public static OrgRole toOrgRole(OrgRoleDto orgRoleDto){
        OrgRole orgRole = new OrgRole();
        if(orgRoleDto.getId() > 0) orgRole.setId(orgRoleDto.getId());
        orgRole.setName(orgRoleDto.getName());
        orgRole.setCompany(OrganizationAssembler.toCompany(orgRoleDto.getOrganization()));
        orgRole.setOrgRoleGroup(OrgRoleGroupAssembler.toEntity(orgRoleDto.getRoleGroup()));
        orgRole.setCreator(orgRoleDto.getCreator());
        return orgRole;
    }

    public static OrgRoleDto toOrgRoleDto(OrgRole orgRole){
        OrgRoleDto.Builder builder = OrgRoleDto.newBuilder();
        builder.setId(orgRole.getId());
        builder.setName(orgRole.getName());
        builder.setOrganization(OrganizationAssembler.toDTO(orgRole.getCompany()));
        builder.setRoleGroup(OrgRoleGroupAssembler.toDto(orgRole.getOrgRoleGroup()));
        builder.setCreator(orgRole.getCreator());
        builder.setCreated(orgRole.getCreated());
        return builder.build();
    }

    public static OrgRoleGroupDto toRoleGroupDto(OrgRoleGroup orgRoleGroup){
        return OrgRoleGroupDto.newBuilder()
                .setId(orgRoleGroup.getId())
                .setName(orgRoleGroup.getName())
                .build();
    }

    public static OrgRoleGroup toRoleGroup(OrgRoleGroupDto groupDto){
        var roleGroup = new OrgRoleGroup();
        if(groupDto.getId() > 0)roleGroup.setId(groupDto.getId());
        roleGroup.setName(groupDto.getName());
        roleGroup.setCompany(OrganizationAssembler.toCompany(groupDto.getOrganization()));
        return roleGroup;
    }
}
