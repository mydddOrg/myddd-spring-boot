package org.myddd.extensions.organization.application;

import com.google.protobuf.Int64Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.organisation.EmployeeNotExistsException;
import org.myddd.extensions.organisation.PermissionGroupNotExistsException;
import org.myddd.extensions.organization.AbstractTest;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.api.UserApplication;

import javax.inject.Inject;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

class TestPermissionGroupApplication extends AbstractTest {

    private OrganizationDto parentOrg;

    @Inject
    private UserApplication userApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private PermissionGroupApplication permissionGroupApplication;

    @BeforeEach
    void beforeEach() {
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        parentOrg = organizationApplication.createTopOrganization(randomOrganization());
    }

    @Test
    void testCreatePermissionGroup() {
        PermissionGroupDto created = createPermissionGroupDto();
        Assertions.assertNotNull(created);
        Assertions.assertFalse(created.getOrganizationsList().isEmpty());
        Assertions.assertFalse(created.getEmployeesList().isEmpty());
        Assertions.assertTrue(created.getOrgRolesList().isEmpty());

        PermissionGroupDto notValidPermissionGroupDto =
                PermissionGroupDto
                        .newBuilder()
                        .setRelateId(randomId())
                        .setType(PermissionGroupTypeDto.PERMISSION_GROUP_TYPE_FORM)
                        .addAllEmployees(Set.of(randomEmployee()))
                        .build();

        Assertions.assertThrows(EmployeeNotExistsException.class,() -> permissionGroupApplication.createPermissionGroup(notValidPermissionGroupDto));
    }

    @Test
    void testQueryPermissionGroup(){
        var permissionGroupNotExistsId = Int64Value.of(-1L);
        Assertions.assertThrows(PermissionGroupNotExistsException.class,()->permissionGroupApplication.queryPermissionGroup(permissionGroupNotExistsId));

        PermissionGroupDto created = createPermissionGroupDto();
        PermissionGroupDto query = permissionGroupApplication.queryPermissionGroup(Int64Value.of(created.getId()));
        Assertions.assertNotNull(query);
        Assertions.assertFalse(query.getOrganizationsList().isEmpty());
        Assertions.assertFalse(query.getEmployeesList().isEmpty());
        Assertions.assertTrue(query.getOrgRolesList().isEmpty());
    }

    @Test
    void testListQueryPermissionGroups(){
        ListPermissionGroupDto permissionGroupDto = permissionGroupApplication.listQueryPermissionGroups(
                ListQueryPermissionGroupDto.newBuilder()
                        .setRelateId(randomId())
                        .setType(PermissionGroupTypeDto.PERMISSION_GROUP_TYPE_FORM)
                        .build()
        );

        Assertions.assertTrue(permissionGroupDto.getPermissionGroupsList().isEmpty());

        PermissionGroupDto created = createPermissionGroupDto();
        permissionGroupDto = permissionGroupApplication.listQueryPermissionGroups(
                ListQueryPermissionGroupDto.newBuilder()
                        .setRelateId(created.getRelateId())
                        .setType(PermissionGroupTypeDto.valueOf(created.getType().toString()))
                        .build()
        );
        Assertions.assertFalse(permissionGroupDto.getPermissionGroupsList().isEmpty());
    }

    @Test
    void testCheckEmployeeInPermissionGroup(){
        PermissionGroupDto created = createPermissionGroupDto();
        var randomEmployee = created.getEmployeesList().stream().findFirst().get();
        var employeeInPermissionGroup = permissionGroupApplication.checkEmployeeInPermissionGroup(
                EmployeeInPermissionGroupDto.newBuilder()
                        .setPermissionId(created.getId())
                        .setEmployeeId(randomEmployee.getId())
                        .build()
        );
        Assertions.assertTrue(employeeInPermissionGroup.getValue());

        var permissionNotInPermissionGroup = permissionGroupApplication.checkEmployeeInPermissionGroup(
                EmployeeInPermissionGroupDto.newBuilder()
                        .setPermissionId(created.getId())
                        .setEmployeeId(-1L)
                        .build()
        );
        Assertions.assertFalse(permissionNotInPermissionGroup.getValue());
    }

    @Test
    void testDeletePermissionGroup(){
        PermissionGroupDto created = createPermissionGroupDto();
        Assertions.assertTrue(permissionGroupApplication.deletePermissionGroup(
                RemovePermissionGroupDto.newBuilder()
                        .setPermissionGroupId(created.getId())
                        .build()
        ).getValue());
    }

    @Test
    void testUpdatePermissionGroup(){

        var permissionGroupNotExistDto =  PermissionGroupDto.newBuilder().setId(-1L).build();
        Assertions.assertThrows(PermissionGroupNotExistsException.class,()-> permissionGroupApplication.updatePermissionGroup(permissionGroupNotExistDto));

        PermissionGroupDto created = createPermissionGroupDto();

        PermissionGroupDto updateDto = PermissionGroupDto.newBuilder()
                .setId(created.getId())
                .setType(created.getType())
                .setRelateId(created.getRelateId())
                .addAllEmployees(Set.of(createEmployeeDto()))
                .build();

        PermissionGroupDto updated = permissionGroupApplication.updatePermissionGroup(updateDto);
        Assertions.assertNotNull(updated);
        Assertions.assertFalse(updated.getEmployeesList().isEmpty());
        Assertions.assertTrue(updated.getOrgRolesList().isEmpty());
        Assertions.assertTrue(updated.getOrganizationsList().isEmpty());

        PermissionGroupDto queryUpdated = permissionGroupApplication.queryPermissionGroup(Int64Value.of(updated.getId()));
        Assertions.assertNotNull(queryUpdated);
        Assertions.assertFalse(queryUpdated.getEmployeesList().isEmpty());
        Assertions.assertTrue(queryUpdated.getOrgRolesList().isEmpty());
        Assertions.assertTrue(queryUpdated.getOrganizationsList().isEmpty());
    }

    private PermissionGroupDto createPermissionGroupDto(){
        PermissionGroupDto permissionGroupDto =
                PermissionGroupDto
                        .newBuilder()
                        .setRelateId(randomId())
                        .setType(PermissionGroupTypeDto.PERMISSION_GROUP_TYPE_FORM)
                        .addAllEmployees(Set.of(createEmployeeDto(),createEmployeeDto()))
                        .addAllOrganizations(Set.of(createOrganizationDto()))
                        .build();

        PermissionGroupDto created = permissionGroupApplication.createPermissionGroup(permissionGroupDto);
        Assertions.assertNotNull(created);
        return created;
    }

    private OrganizationDto createOrganizationDto() {
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        return organizationApplication.createDepartment(randomDepartment(parentOrg));
    }

    private EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = randomCreateEmployee();
        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(employeeDto.getId())
                        .setOrgId(parentOrg.getId())
                        .build()
        );
        return employeeDto;
    }

    private EmployeeDto randomCreateEmployee(){
        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        EmployeeDto created = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        Assertions.assertNotNull(created);
        return created;
    }
}
