package org.myddd.extensions.organisation.organization.controller;

import com.google.protobuf.Int64Value;
import org.myddd.extensions.organisation.organization.*;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.utils.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.stream.Collectors;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class EmployeeController {

    @Inject
    private IAuthentication authentication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @GetMapping("/current-login-user/organizations/{orgId}/employee")
    public ResponseEntity<EmployeeVO> queryCurrentLoginUserEmployeeInfo(@PathVariable Long orgId){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var currentUserId = authentication.loginUserId();
        var queryEmployee = employeeApplication.queryEmployeeByUserIdAndOrgId(
                QueryEmployeeByUserAndOrg.newBuilder()
                        .setUserId(currentUserId)
                        .setOrgId(orgId)
                        .build()
        );


        if(!queryEmployee.hasEmployee()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var employee = queryEmployee.getEmployee();

        var systemManagerEmployees = organizationApplication.queryOrganizationSystemManagerEmployees(Int64Value.of(orgId));
        var systemMangerIds = systemManagerEmployees.getEmployeesList().stream().map(EmployeeDto::getId).collect(Collectors.toList());
        var employeeVO = EmployeeVO.of(employee);
        employeeVO.setSystemManager(systemMangerIds.contains(employeeVO.getId()));

        return ResponseEntity.ok(EmployeeVO.of(employee));
    }

    @PostMapping("/employeeAssigner")
    public ResponseEntity<Void> assignEmployeeToOrganization(@RequestBody AssignEmployeeToOrganizationVO assignEmployeeToOrganizationVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        assignEmployeeToOrganizationVO.setOperator(authentication.loginUserId());
        employeeApplication.assignEmployeeToOrganization(assignEmployeeToOrganizationVO.toDto());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/organizations/{orgId}/all-employees")
    public ResponseEntity<Page<EmployeeVO>> queryAllEmployeesByInOrg(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize, @PathVariable long orgId){
        EmployeePageQueryDto pageQueryDto = EmployeePageQueryDto.newBuilder()
                .setPageSize(pageSize)
                .setPage(page)
                .setOrgId(orgId)
                .setSearch(search)
                .build();

        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(pageQueryDto);

        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(pageEmployeeDto.getPageSize())
                        .totalSize(pageEmployeeDto.getTotal())
                        .data(pageEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
                        .start(pageEmployeeDto.getPageSize() * pageEmployeeDto.getPage())
        );
    }

    @PostMapping("/organizations/{orgId}/all-employees/page-query")
    public ResponseEntity<Page<EmployeeVO>> queryAllEmployeesByInOrgWithLimits(@PathVariable long orgId,@RequestBody EmployeeWithLimitsPageQueryVO pageQueryVO){
        EmployeePageQueryDto pageQueryDto = EmployeePageQueryDto.newBuilder()
                .setPageSize(pageQueryVO.getPageSize())
                .setPage(pageQueryVO.getPage())
                .setOrgId(orgId)
                .setSearch(pageQueryVO.getSearch())
                .addAllEmployeeLimits(pageQueryVO.getEmployees())
                .addAllOrganizationLimits(pageQueryVO.getOrganizations())
                .addAllRoleLimits(pageQueryVO.getRoles())
                .build();

        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryAllEmployeesInOrg(pageQueryDto);

        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(pageEmployeeDto.getPageSize())
                        .totalSize(pageEmployeeDto.getTotal())
                        .start(pageEmployeeDto.getPageSize() * pageEmployeeDto.getPage())
                        .data(pageEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
        );
    }

    @GetMapping("/organizations/{orgId}/employees")
    public ResponseEntity<Page<EmployeeVO>> queryEmployeesByOrg(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize, @PathVariable long orgId){
        PageQueryDto pageQueryDto = PageQueryDto.newBuilder()
                .setPageSize(pageSize)
                .setPage(page)
                .setOrgId(orgId)
                .build();

        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryEmployeesByOrg(pageQueryDto);


        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(pageEmployeeDto.getPageSize())
                        .totalSize(pageEmployeeDto.getTotal())
                        .data(pageEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
                        .start(pageEmployeeDto.getPageSize() * pageEmployeeDto.getPage())
        );
    }

    @PostMapping("/organizations/{orgId}/employees/page-query")
    public ResponseEntity<Page<EmployeeVO>> queryEmployeesByOrgWithLimit(@PathVariable long orgId,@RequestBody WithLimitsPageQueryVO withLimitsPageQueryVO){
        PageQueryDto pageQueryDto = PageQueryDto.newBuilder()
                .setPageSize(withLimitsPageQueryVO.getPageSize())
                .setPage(withLimitsPageQueryVO.getPage())
                .setSearch(withLimitsPageQueryVO.getSearch())
                .setOrgId(orgId)
                .addAllLimits(withLimitsPageQueryVO.getLimits())
                .build();

        PageEmployeeDto pageEmployeeDto = employeeApplication.pageQueryEmployeesByOrg(pageQueryDto);

        return ResponseEntity.ok(
                Page.builder(EmployeeVO.class)
                        .pageSize(pageEmployeeDto.getPageSize())
                        .totalSize(pageEmployeeDto.getTotal())
                        .data(pageEmployeeDto.getEmployeesList().stream().map(EmployeeVO::of).collect(Collectors.toList()))
                        .start(pageEmployeeDto.getPageSize() * pageEmployeeDto.getPage())
        );
    }

    @PutMapping("/organizations/{orgId}/employees/re-assigner")
    public ResponseEntity<Void> reAssignEmployeeListToOrganizationList(@RequestBody ReAssignEmployeesToOrganizationsVO reAssignEmployeesToOrganizationsVO){
        employeeApplication.reAssignEmployeesToOrganizations(
                ReAssignEmployeesToOrganizationsDto.newBuilder()
                        .addAllEmployeeIdList(reAssignEmployeesToOrganizationsVO.getEmployees())
                        .addAllOrgIdList(reAssignEmployeesToOrganizationsVO.getOrganizations())
                .build()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/organizations/{orgId}/employees")
    public ResponseEntity<EmployeeVO> createEmployee(@PathVariable long orgId,@RequestBody EmployeeVO employeeVO){
        employeeVO.setOrgId(orgId);
        var createdEmployee = employeeApplication.createEmployee(employeeVO.toDto());
        return ResponseEntity.ok(EmployeeVO.of(createdEmployee));
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable long employeeId,@RequestBody EmployeeVO employeeVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        employeeVO.setId(employeeId);

        EmployeeDto updatedEmployeeDto = employeeApplication.updateEmployee(employeeVO.toDto());
        return ResponseEntity.ok(EmployeeVO.of(updatedEmployeeDto));
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeVO> queryEmployee(@PathVariable long employeeId){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var queryEmployee = employeeApplication.queryEmployee(Int64Value.of(employeeId));
        if(!queryEmployee.hasEmployee()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(EmployeeVO.of(queryEmployee.getEmployee()));
    }
}
