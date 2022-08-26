package org.myddd.extensions.organization.application.grpc;

import com.google.protobuf.BoolValue;
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
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

class EmployeeApplicationGrpcTest extends AbstractGrpcTest {

    @Inject
    private UserApplication userApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Test
    void testCreateEmployee() {
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        EmployeeDto created = randomCreateEmployee();
        Assertions.assertNotNull(created);
        var emptyEmployeeDto = EmployeeDto.newBuilder().build();
        Assertions.assertThrows(StatusRuntimeException.class, () -> employeeApplication.createEmployee(emptyEmployeeDto));

        var subOrg = randomDepartment(OrganizationDto.newBuilder().setId(created.getOrgId()).build());
        var createdSubOrg = organizationApplication.createDepartment(subOrg);
        Assertions.assertDoesNotThrow(()->employeeApplication.createEmployee(randomEmployeeWithSubOrgIds(created.getOrgId(), List.of(created.getOrgId(),createdSubOrg.getId()))));

        var notValidSubOrgIdDto = randomEmployeeWithSubOrgIds(created.getOrgId(),List.of(randomLong()));
        Assertions.assertThrows(StatusRuntimeException.class,()->employeeApplication.createEmployee(notValidSubOrgIdDto));
    }

    @Test
    void testQueryEmployeeByUserIdAndOrgId(){
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());


        var optionalEmployeeDto = employeeApplication.queryEmployeeByUserIdAndOrgId(
                QueryEmployeeByUserAndOrg.newBuilder()
                        .setOrgId(-1L)
                        .setUserId(randomLong())
                        .build()
        );
        Assertions.assertFalse(optionalEmployeeDto.hasEmployee());

        EmployeeDto created = randomCreateEmployee();

        Assertions.assertNotNull(
                employeeApplication.queryEmployeeByUserIdAndOrgId(
                        QueryEmployeeByUserAndOrg.newBuilder()
                                .setOrgId(created.getOrgId())
                                .setUserId(created.getUserId())
                                .build()
                )
        );
    }

    @Test
    void testUpdateEmployee(){
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        EmployeeDto created = randomCreateEmployee();
        Assertions.assertNotNull(created);

        String newName = randomId();
        EmployeeDto updatedDto = EmployeeDto.newBuilder()
                .setId(created.getId())
                .setName(newName)
                .build();

        EmployeeDto updated = employeeApplication.updateEmployee(updatedDto);
        Assertions.assertEquals(newName,updated.getName());
    }

    @Test
    void testQueryEmployee() {
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        EmployeeDto created = randomCreateEmployee();
        Assertions.assertNotNull(created);

        var queryEmployee = employeeApplication.queryEmployee(Int64Value.of(created.getId()));
        Assertions.assertTrue(queryEmployee.hasEmployee());

        var notExistsQuery = employeeApplication.queryEmployee(Int64Value.getDefaultInstance());
        Assertions.assertFalse(notExistsQuery.hasEmployee());
    }

    @Test
    void testAssignEmployeeToOrganization() {
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        AssignEmployeeToOrganizationDto errorDto1 =
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(-1L)
                        .setOrgId(-1L)
                        .build();

        Assertions.assertThrows(StatusRuntimeException.class, () -> employeeApplication.assignEmployeeToOrganization(errorDto1));

        OrganizationDto createdCompany = organizationApplication.createTopOrganization(randomOrganization());

        AssignEmployeeToOrganizationDto errorDto2 =
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(-1L)
                        .setOrgId(createdCompany.getId())
                        .build();

        Assertions.assertThrows(StatusRuntimeException.class, () -> employeeApplication.assignEmployeeToOrganization(errorDto2));

        EmployeeDto created = randomCreateEmployee();

        AssignEmployeeToOrganizationDto assignEmployeeToOrganizationDto =
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(created.getId())
                        .setOrgId(createdCompany.getId())
                        .build();


        BoolValue success = employeeApplication.assignEmployeeToOrganization(assignEmployeeToOrganizationDto);
        Assertions.assertTrue(success.getValue());
    }

    @Test
    void testPageQueryEmployeesByOrg() {
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());


        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryEmployeesByOrg(PageQueryDto.newBuilder()
                .setOrgId(-1L)
                .setPage(0)
                .setPageSize(10)
                .build());

        Assertions.assertEquals(0,pageEmployeeDto.getTotal());

        OrganizationDto createdCompany = organizationApplication.createTopOrganization(randomOrganization());
        pageEmployeeDto = employeeApplication.pageQueryEmployeesByOrg(PageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .build());

        Assertions.assertTrue(pageEmployeeDto.getTotal() > 0);
        Assertions.assertNotNull(pageEmployeeDto.getEmployeesList().stream().findAny().get().getOrganizationList().stream().findAny().get().getFullNamePath());
        Assertions.assertFalse(pageEmployeeDto.getEmployeesList().isEmpty());
    }

    @Test
    void testQueryAllEmployeesInOrg(){
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryEmployeesByOrg(PageQueryDto.newBuilder()
                .setOrgId(-1L)
                .setPage(0)
                .setPageSize(10)
                .build());

        Assertions.assertEquals(0,pageEmployeeDto.getTotal());

        OrganizationDto createdCompany = organizationApplication.createTopOrganization(randomOrganization());
        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .build());

        Assertions.assertTrue(pageEmployeeDto.getTotal() > 0);
        Assertions.assertFalse(pageEmployeeDto.getEmployeesList().isEmpty());

        //query by search
        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .setSearch(randomId())
                .build());

        Assertions.assertEquals(0,pageEmployeeDto.getTotal());
        Assertions.assertTrue(pageEmployeeDto.getEmployeesList().isEmpty());


        //query with organizations limits
        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .addOrganizationLimits(createdCompany.getId())
                .build());

        Assertions.assertEquals(1,pageEmployeeDto.getTotal());
        Assertions.assertFalse(pageEmployeeDto.getEmployeesList().isEmpty());

        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .addOrganizationLimits(randomLong())
                .build());

        Assertions.assertEquals(0,pageEmployeeDto.getTotal());
        Assertions.assertTrue(pageEmployeeDto.getEmployeesList().isEmpty());

        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .addOrganizationLimits(createdCompany.getId())
                .addRoleLimits(randomLong())
                .build());

        Assertions.assertEquals(1,pageEmployeeDto.getTotal());
        Assertions.assertFalse(pageEmployeeDto.getEmployeesList().isEmpty());

        pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder()
                .setOrgId(createdCompany.getId())
                .setPage(0)
                .setPageSize(10)
                .addOrganizationLimits(createdCompany.getId())
                .addRoleLimits(randomLong())
                .addEmployeeLimits(randomLong())
                .build());

        Assertions.assertEquals(1,pageEmployeeDto.getTotal());
        Assertions.assertFalse(pageEmployeeDto.getEmployeesList().isEmpty());
    }

    @Test
    void  testReAssignEmployeesToOrganizations(){
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);


        var company = organizationApplication.createTopOrganization(randomOrganization());
        var createdEmployee = employeeApplication.createEmployee(randomEmployee(company.getId()));
        var subDepartment = organizationApplication.createDepartment(randomDepartment(company));

        Assertions.assertDoesNotThrow(() -> {
            employeeApplication.reAssignEmployeesToOrganizations(
                    ReAssignEmployeesToOrganizationsDto.newBuilder()
                            .addEmployeeIdList(createdEmployee.getId())
                            .addOrgIdList(subDepartment.getId())
                            .build()
            );
        });
    }

    private EmployeeDto randomEmployeeWithSubOrgIds(Long orgId,List<Long> subOrgIds){
        return EmployeeDto.newBuilder()
                .setRelateUserId(randomId())
                .setUserId(randomLong())
                .setOrgId(orgId)
                .setEmail(randomId())
                .setPhone(randomId())
                .addAllOrganization(
                        subOrgIds.stream().map(it -> OrganizationDto.newBuilder().setId(it).build()).collect(Collectors.toList())
                )
                .setName(randomId()).build();
    }

    private EmployeeDto randomCreateEmployee(){
        var employeeApplication = EmployeeApplicationGrpc.newBlockingStub(managedChannel);

        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        EmployeeDto created = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        Assertions.assertNotNull(created);
        return created;
    }

}
