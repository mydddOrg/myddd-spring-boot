package org.myddd.extensions.organization.application.assembler;


import org.myddd.extensions.organisation.domain.PermissionGroup;
import org.myddd.extensions.organisation.domain.PermissionGroupType;
import org.myddd.extensions.organization.api.PermissionGroupDto;
import org.myddd.extensions.organization.api.PermissionGroupTypeDto;

import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionGroupAssembler {

    public static PermissionGroup toPermissionGroup(PermissionGroupDto permissionGroupDto){
        if(Objects.isNull(permissionGroupDto)){
            return null;
        }
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setType(PermissionGroupType.valueOf(permissionGroup.getType().toString()));
        permissionGroup.setRelateId(permissionGroupDto.getRelateId());
        permissionGroup.setEmployees(
                permissionGroupDto.getEmployeesList().stream().map(EmployeeAssembler::toEmployee).collect(Collectors.toSet())
        );
        permissionGroup.setOrganizations(
                permissionGroupDto.getOrganizationsList().stream().map(OrganizationAssembler::toCompany).collect(Collectors.toSet())
        );
        permissionGroup.setRoleSet(
                permissionGroupDto.getOrgRolesList().stream().map(OrgRoleAssembler::toOrgRole).collect(Collectors.toSet())
        );
        return permissionGroup;
    }

    public static PermissionGroupDto toPermissionGroupDto(PermissionGroup permissionGroup){
        if(Objects.isNull(permissionGroup)){
            return null;
        }
        return PermissionGroupDto.newBuilder()
                .setId(permissionGroup.getId())
                .setRelateId(permissionGroup.getRelateId())
                .setType(PermissionGroupTypeDto.valueOf(permissionGroup.getType().toString()))
                .addAllOrganizations(
                        permissionGroup.getOrganizations().stream().map(OrganizationAssembler::toDTO).collect(Collectors.toSet())
                )
                .addAllEmployees(
                        permissionGroup.getEmployees().stream().map(EmployeeAssembler::toDto).collect(Collectors.toSet())
                )
                .addAllOrgRoles(
                        permissionGroup.getRoleSet().stream().map(OrgRoleAssembler::toOrgRoleDto).collect(Collectors.toSet())
                )
                .build();
    }

}
