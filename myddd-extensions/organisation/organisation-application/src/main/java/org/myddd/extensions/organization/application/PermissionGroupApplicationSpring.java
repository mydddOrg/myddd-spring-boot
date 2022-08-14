package org.myddd.extensions.organization.application;

import org.myddd.extensions.organisation.PermissionGroupNotExistsException;
import org.myddd.extensions.organisation.domain.PermissionGroup;
import org.myddd.extensions.organisation.domain.PermissionGroupCreatorService;
import org.myddd.extensions.organisation.domain.PermissionGroupService;
import org.myddd.extensions.organisation.domain.PermissionGroupType;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.organization.application.assembler.PermissionGroupAssembler;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class PermissionGroupApplicationSpring implements PermissionGroupApplication {
    @Override
    @Transactional
    public PermissionGroupDto createPermissionGroup(PermissionGroupDto request) {

        PermissionGroup created = PermissionGroupCreatorService.createPermissionGroup(
                PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                        .relationId(request.getRelateId())
                        .type(PermissionGroupType.valueOf(request.getType().toString()))
                        .employeeIds(request.getEmployeesList().stream().map(EmployeeDto::getId).collect(Collectors.toSet()))
                        .organizationIds(request.getOrganizationsList().stream().map(OrganizationDto::getId).collect(Collectors.toSet()))
                        .roleIds(request.getOrgRolesList().stream().map(OrgRoleDto::getId).collect(Collectors.toSet()))
        );

        return PermissionGroupAssembler.toPermissionGroupDto(created);
    }

    @Override
    public PermissionGroupDto queryPermissionGroup(Int64Value request) {
        PermissionGroup permissionGroup = PermissionGroupService.queryPermissionGroup(request.getValue());
        return PermissionGroupAssembler.toPermissionGroupDto(permissionGroup);
    }

    @Override
    @Transactional
    public PermissionGroupDto updatePermissionGroup(PermissionGroupDto request) {
        boolean exists = PermissionGroup.permissionGroupExists(request.getId());
        if(!exists){
            throw new PermissionGroupNotExistsException(request.getId());
        }
        PermissionGroupService.clearPermissionGroupById(request.getId());

        PermissionGroup created = PermissionGroupCreatorService.updatePermissionGroup(
                PermissionGroupCreatorService.PermissionGroupCreator.createInstance()
                        .id(request.getId())
                        .relationId(request.getRelateId())
                        .type(PermissionGroupType.valueOf(request.getType().toString()))
                        .employeeIds(request.getEmployeesList().stream().map(EmployeeDto::getId).collect(Collectors.toSet()))
                        .organizationIds(request.getOrganizationsList().stream().map(OrganizationDto::getId).collect(Collectors.toSet()))
                        .roleIds(request.getOrgRolesList().stream().map(OrgRoleDto::getId).collect(Collectors.toSet()))
        );
        return PermissionGroupAssembler.toPermissionGroupDto(created);
    }

    @Override
    public ListPermissionGroupDto listQueryPermissionGroups(ListQueryPermissionGroupDto request) {
        List<PermissionGroup> permissionGroupList = PermissionGroupService.listPermissionGroupByTypeAndRelateId(PermissionGroupType.valueOf(request.getType().toString()),request.getRelateId());
        return ListPermissionGroupDto.newBuilder()
                .addAllPermissionGroups(permissionGroupList.stream().map(PermissionGroupAssembler::toPermissionGroupDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    @Transactional
    public BoolValue deletePermissionGroup(RemovePermissionGroupDto request) {
        PermissionGroupService.removePermissionGroupById(request.getPermissionGroupId());
        return BoolValue.of(true);
    }

    @Override
    public ListPermissionGroupDto queryEmployeePermissionGroups(QueryEmployeePermissionGroupDto request) {
        List<PermissionGroup> permissionGroupList = PermissionGroupService.queryEmployeePermissionGroupInRelateScope(PermissionGroupType.valueOf(request.getType().toString()),request.getRelateId(),request.getEmployeeId());
        return ListPermissionGroupDto.newBuilder()
                .addAllPermissionGroups(permissionGroupList.stream().map(PermissionGroupAssembler::toPermissionGroupDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public BoolValue checkEmployeeInPermissionGroup(EmployeeInPermissionGroupDto request) {
        var permissionGroup = PermissionGroupService.queryPermissionGroup(request.getPermissionId());
        ListPermissionGroupDto permissionGroupDto = queryEmployeePermissionGroups(QueryEmployeePermissionGroupDto
                .newBuilder()
                .setEmployeeId(request.getEmployeeId())
                .setType(PermissionGroupTypeDto.valueOf(permissionGroup.getType().toString()))
                .setRelateId(permissionGroup.getRelateId())
                .build());
        var contains =  permissionGroupDto.getPermissionGroupsList().stream().map(PermissionGroupDto::getId).collect(Collectors.toList()).contains(request.getPermissionId());
        return BoolValue.of(contains);
    }
}
