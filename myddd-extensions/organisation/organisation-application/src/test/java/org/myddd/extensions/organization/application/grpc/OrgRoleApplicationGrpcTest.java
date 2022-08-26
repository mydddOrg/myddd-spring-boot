package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.Int64Value;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.organization.AbstractGrpcTest;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.api.UserApplication;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class OrgRoleApplicationGrpcTest extends AbstractGrpcTest {


    @Inject
    private UserApplication userApplication;

    private OrgRoleApplicationGrpc.OrgRoleApplicationBlockingStub orgRoleApplication = OrgRoleApplicationGrpc.newBlockingStub(managedChannel);

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Test
    void testCreateOrgRole() {
        OrgRoleDto notValidOrgRoleDto = randomOrgRoleDto();
        Assertions.assertThrows(StatusRuntimeException.class,() -> orgRoleApplication.createOrgRole(notValidOrgRoleDto));
        OrgRoleDto createdOrgRoleDto = createOrgRoleDto();
        Assertions.assertNotNull(createdOrgRoleDto);
        Assertions.assertTrue(createdOrgRoleDto.getRoleGroup().getId() > 0);
    }

    @Test
    void testRemoveOrgRole(){
        var notValidOrgId = Int64Value.of(randomLong());
        Assertions.assertThrows(StatusRuntimeException.class,()->orgRoleApplication.removeOrgRole(notValidOrgId));

        OrgRoleDto createdOrgRoleDto = createOrgRoleDto();
        Assertions.assertDoesNotThrow(()->orgRoleApplication.removeOrgRole(Int64Value.of(createdOrgRoleDto.getId())));
    }

    @Test
    void testListOrgRoles(){
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        var company = organizationApplication.createTopOrganization(randomOrganization());
        var emptyList = orgRoleApplication.listOrgRoles(ListOrgQueryDto.newBuilder()
                .setOrgId(company.getId()).build());
        Assertions.assertTrue(emptyList.getRolesList().isEmpty());

        var createdOrgRoleDto = createOrgRoleDto();
        var notEmptyList = orgRoleApplication.listOrgRoles(ListOrgQueryDto.newBuilder().setOrgId(createdOrgRoleDto.getOrganization().getId()).build());
        Assertions.assertFalse(notEmptyList.getRolesList().isEmpty());
    }

    @Test
    void testCreateOrgRoleGroup(){
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        var company = organizationApplication.createTopOrganization(randomOrganization());
        var created = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(company.getId()));
        Assertions.assertNotNull(created);
    }

    @Test
    void testUpdateOrgRoleGroup(){
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        var company = organizationApplication.createTopOrganization(randomOrganization());
        var created = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(company.getId()));

        var updateOrgRoleGroup = OrgRoleGroupDto.newBuilder()
                .setId(created.getId())
                .setName(randomId())
                .build();
        Assertions.assertDoesNotThrow(()->orgRoleApplication.updateOrgRoleGroup(updateOrgRoleGroup));
    }

    @Test
    void testRemoveOrgRoleGroup(){
        var notValidRoleGroupId = Int64Value.of(randomLong());
        Assertions.assertThrows(StatusRuntimeException.class,()-> orgRoleApplication.removeRoleGroup(notValidRoleGroupId));
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        OrgRoleDto createdOrgRoleDto = createOrgRoleDto();

        var notValidGroupId = Int64Value.of(createdOrgRoleDto.getRoleGroup().getId());
        Assertions.assertThrows(StatusRuntimeException.class,() -> orgRoleApplication.removeRoleGroup(notValidGroupId));

        var company = organizationApplication.createTopOrganization(randomOrganization());
        var created = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(company.getId()));
        Assertions.assertDoesNotThrow(()->orgRoleApplication.removeRoleGroup(Int64Value.of(created.getId())));
    }

    @Test
    void testUpdateOrgRole() {
        UpdateOrgRoleDto notValidUpdateOrgRoleDto = UpdateOrgRoleDto.newBuilder()
                .setId(1L)
                .setName(randomId())
                .build();

        Assertions.assertThrows(StatusRuntimeException.class,() -> orgRoleApplication.updateOrgRole(notValidUpdateOrgRoleDto));
        String newName = randomId();
        OrgRoleDto createdOrgRoleDto = createOrgRoleDto();
        UpdateOrgRoleDto updateOrgRoleDto = UpdateOrgRoleDto.newBuilder()
                .setId(createdOrgRoleDto.getId())
                .setName(newName)
                .build();

        OrgRoleDto updated = orgRoleApplication.updateOrgRole(updateOrgRoleDto);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(newName,updated.getName());

        //update role group
        var anotherRoleGroup = randomOrgRoleGroup(updated.getOrganization().getId());
        updateOrgRoleDto = UpdateOrgRoleDto.newBuilder()
                .setId(createdOrgRoleDto.getId())
                .setRoleGroupId(anotherRoleGroup.getId())
                .build();

        updated = orgRoleApplication.updateOrgRole(updateOrgRoleDto);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(newName,updated.getName());
    }

    @Test
    void testAssignEmployeeToRole() {
        EmployeeDto employee = randomCreateEmployee();

        OrgRoleDto orgRole = createOrgRoleDto(employee.getOrgId());
        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setOrgId(orgRole.getOrganization().getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );


        Assertions.assertDoesNotThrow(() -> orgRoleApplication.assignEmployeeToOrgRole(
                EmployeeOrgRoleRelationDto.newBuilder()
                        .setOrgRoleId(orgRole.getId())
                        .setEmployeeId(employee.getId())
                        .build()));
    }

    @Test
    void testBatchAssignEmployeeToRole(){
        var employee = randomCreateEmployee();
        var orgRole = createOrgRoleDto(employee.getOrgId());

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setOrgId(orgRole.getOrganization().getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );


        Assertions.assertDoesNotThrow(() -> orgRoleApplication.batchAssignEmployeesToOrgRole(
                BatchEmployeeForRoleDto.newBuilder()
                        .setOrgRoleId(orgRole.getId())
                        .addAllEmployeeIds(List.of(employee.getId()))
                        .build()));
    }

    @Test
    void testBatchDeAssignEmployeeToRole(){
        var employee = randomCreateEmployee();
        var orgRole = createOrgRoleDto(employee.getOrgId());

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setOrgId(orgRole.getOrganization().getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );


        Assertions.assertDoesNotThrow(() -> orgRoleApplication.batchAssignEmployeesToOrgRole(
                BatchEmployeeForRoleDto.newBuilder()
                        .setOrgRoleId(orgRole.getId())
                        .addAllEmployeeIds(List.of(employee.getId()))
                        .build()));



        Assertions.assertDoesNotThrow(() -> orgRoleApplication.batchDeAssignEmployeesToOrlRole(
                BatchEmployeeForRoleDto.newBuilder()
                        .setOrgRoleId(orgRole.getId())
                        .addAllEmployeeIds(List.of(employee.getId()))
                        .build()));
    }

    @Test
    void testDeAssignEmployeeFromRole() {
        EmployeeDto employee = randomCreateEmployee();
        OrgRoleDto orgRole = createOrgRoleDto(employee.getOrgId());
        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setOrgId(orgRole.getOrganization().getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );

        orgRoleApplication.assignEmployeeToOrgRole(
                EmployeeOrgRoleRelationDto.newBuilder()
                        .setOrgRoleId(orgRole.getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );


        Assertions.assertDoesNotThrow(
                () -> orgRoleApplication.deAssignEmployeeFromOrgRole(EmployeeOrgRoleRelationDto
                        .newBuilder()
                        .setEmployeeId(employee.getId())
                        .setOrgRoleId(orgRole.getId())
                        .build())
        );
    }

    @Test
    void testPageQueryOrgRoles(){

        PageOrgRole notExistsOrgRole = orgRoleApplication.pageQueryOrgRolesByOrg(
                PageOrgRoleQueryDto.newBuilder()
                        .setOrgId(-1L)
                        .setPage(0)
                        .setPageSize(10)
                        .build()
        );
        Assertions.assertTrue(notExistsOrgRole.getOrgRolesList().isEmpty());

        OrgRoleDto createdOrgRoleDto = createOrgRoleDto();
        PageOrgRole pageOrgRole = orgRoleApplication.pageQueryOrgRolesByOrg(
                PageOrgRoleQueryDto.newBuilder()
                        .setOrgId(createdOrgRoleDto.getOrganization().getId())
                        .setPage(0)
                        .setPageSize(10)
                        .build()
        );
        Assertions.assertFalse(pageOrgRole.getOrgRolesList().isEmpty());

        //query by search
        pageOrgRole = orgRoleApplication.pageQueryOrgRolesByOrg(
                PageOrgRoleQueryDto.newBuilder()
                        .setOrgId(createdOrgRoleDto.getOrganization().getId())
                        .setPage(0)
                        .setPageSize(10)
                        .setSearch(randomId())
                        .build()
        );
        Assertions.assertTrue(pageOrgRole.getOrgRolesList().isEmpty());
    }

    @Test
    void testPageQueryOrgRoleEmployees(){
        PageRoleEmployeeDto pageRoleEmployeeDto = orgRoleApplication.pageQueryEmployeesByRole(
                PageRoleEmployeeQueryDto.newBuilder()
                        .setPage(0)
                        .setPageSize(10)
                        .setRoleId(1L)
                        .build()
        );
        Assertions.assertTrue(pageRoleEmployeeDto.getEmployeesList().isEmpty());

        EmployeeDto employee = randomCreateEmployee();

        OrgRoleDto orgRole = createOrgRoleDto(employee.getOrgId());
        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setOrgId(orgRole.getOrganization().getId())
                        .setEmployeeId(employee.getId())
                        .build()
        );

        Assertions.assertDoesNotThrow(
                () -> orgRoleApplication.assignEmployeeToOrgRole(
                        EmployeeOrgRoleRelationDto.newBuilder()
                                .setOrgRoleId(orgRole.getId())
                                .setEmployeeId(employee.getId())
                                .build())
        );

        pageRoleEmployeeDto = orgRoleApplication.pageQueryEmployeesByRole(
                PageRoleEmployeeQueryDto.newBuilder()
                        .setPage(0)
                        .setPageSize(10)
                        .setRoleId(orgRole.getId())
                        .build()
        );
        Assertions.assertFalse(pageRoleEmployeeDto.getEmployeesList().isEmpty());

        pageRoleEmployeeDto = orgRoleApplication.pageQueryEmployeesByRole(
                PageRoleEmployeeQueryDto.newBuilder()
                        .setPage(0)
                        .setPageSize(10)
                        .setSearch(randomId())
                        .setRoleId(orgRole.getId())
                        .build()
        );
        Assertions.assertTrue(pageRoleEmployeeDto.getEmployeesList().isEmpty());
    }

    @Test
    void testQueryOrgRoleGroupByOrg(){
        var emptyQuery = orgRoleApplication.listQueryRoleGroupsByOrg(Int64Value.of(randomLong()));
        Assertions.assertTrue(emptyQuery.getGroupsList().isEmpty());

        var createdOrgRole = createOrgRoleDto();
        var notEmptyQuery = orgRoleApplication.listQueryRoleGroupsByOrg(Int64Value.of(createdOrgRole.getOrganization().getId()));
        Assertions.assertFalse(notEmptyQuery.getGroupsList().isEmpty());
    }

    @Test
    void testChangeOrgRoleGroup(){

        var notValidOrgRoleGroupDto = ChangeOrgRoleGroupDto.newBuilder().setRoleId(randomLong()).setGroupId(randomLong()).build();
        Assertions.assertThrows(StatusRuntimeException.class,()->orgRoleApplication.changeOrgRoleGroup(notValidOrgRoleGroupDto));

        var createdOrgRole = createOrgRoleDto();

        var notValidGroupDto = ChangeOrgRoleGroupDto.newBuilder().setRoleId(createdOrgRole.getId()).setGroupId(randomLong()).build();
        Assertions.assertThrows(StatusRuntimeException.class, ()->orgRoleApplication.changeOrgRoleGroup(notValidGroupDto));

        var anotherRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdOrgRole.getOrganization().getId()));
        Assertions.assertDoesNotThrow(
                ()->orgRoleApplication.changeOrgRoleGroup(
                        ChangeOrgRoleGroupDto.newBuilder()
                                .setRoleId(createdOrgRole.getId())
                                .setGroupId(anotherRoleGroup.getId())
                                .build()
                ));

    }


    private OrgRoleDto createOrgRoleDto(Long orgId) {
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(orgId));

        OrgRoleDto createdOrgRoleDto = orgRoleApplication.createOrgRole(randomOrgRoleDto(orgId,orgRoleGroup.getId()));
        Assertions.assertNotNull(createdOrgRoleDto);

        return createdOrgRoleDto;
    }

    private OrgRoleDto createOrgRoleDto() {
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        var organization = organizationApplication.createTopOrganization(randomOrganization());
        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(organization.getId()));

        OrgRoleDto createdOrgRoleDto = orgRoleApplication.createOrgRole(randomOrgRoleDto(organization.getId(),orgRoleGroup.getId()));
        Assertions.assertNotNull(createdOrgRoleDto);

        return createdOrgRoleDto;
    }

    private EmployeeDto randomCreateEmployee(){
        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        EmployeeDto created = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        org.junit.jupiter.api.Assertions.assertNotNull(created);
        return created;
    }

}
