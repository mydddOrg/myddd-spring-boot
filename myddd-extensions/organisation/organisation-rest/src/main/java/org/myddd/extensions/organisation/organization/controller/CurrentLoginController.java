package org.myddd.extensions.organisation.organization.controller;

import org.myddd.extensions.organisation.organization.DynamicResultVO;
import org.myddd.extensions.organisation.organization.EmployeeVO;
import org.myddd.extensions.organisation.organization.OrganizationVO;
import com.google.protobuf.Int64Value;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.IAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class CurrentLoginController {

    @Inject
    private IAuthentication authentication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    private static final String MY_EMPLOYEE = "my-employee";

    private static final String MY_DEPARTMENTS = "my-departments";

    private static final String MY_DEPARTMENTS_INCLUDE_CHILDREN = "my-departments-include-children";

    @GetMapping("/current-login-user/organizations/{orgId}/dynamic/{key}")
    ResponseEntity<DynamicResultVO> dynamicQuery(@PathVariable Long orgId, @PathVariable String key){
        if(!authentication.isAuthentication())return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var currentUserId = authentication.loginUserId();
        var queryCurrentEmployee = employeeApplication.queryEmployeeByUserIdAndOrgId(
                QueryEmployeeByUserAndOrg.newBuilder()
                        .setOrgId(orgId)
                        .setUserId(currentUserId)
                        .build()
        );

        var currentEmployee = queryCurrentEmployee.getEmployee();
        if(MY_EMPLOYEE.equals(key)){

            var systemManagerEmployees = organizationApplication.queryOrganizationSystemManagerEmployees(Int64Value.of(orgId));
            var systemMangerIds = systemManagerEmployees.getEmployeesList().stream().map(EmployeeDto::getId).collect(Collectors.toList());
            var employeeVO = EmployeeVO.of(currentEmployee);
            employeeVO.setSystemManager(systemMangerIds.contains(employeeVO.getId()));

            return ResponseEntity.ok(DynamicResultVO.createFromEmployee(employeeVO));
        }else if(MY_DEPARTMENTS.equals(key)){
            var departments = queryMyDepartments(orgId,currentEmployee.getId(),false);
            return ResponseEntity.ok(DynamicResultVO.createFromOrganizations(departments));
        }
        else if(MY_DEPARTMENTS_INCLUDE_CHILDREN.equals(key)){
            var departments = queryMyDepartments(orgId,currentEmployee.getId(),true);
            return ResponseEntity.ok(DynamicResultVO.createFromOrganizations(departments));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private List<OrganizationVO> queryMyDepartments(Long orgId, Long employeeId, boolean includeSub){
        var departments = organizationApplication.queryDepartmentsByEmployeeAndOrg(
                QueryDepartmentsDto.newBuilder()
                        .setOrgId(orgId)
                        .setIncludeSubOrg(includeSub)
                        .setEmployeeId(employeeId)
                        .build()
        );

        return departments.getOrganizationsList().stream().map(OrganizationVO::of).collect(Collectors.toList());
    }
}
