package org.myddd.extensions.organisation.domain;

import org.myddd.extensions.organisation.OrganizationNotExistsException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeService {


    private EmployeeService(){}

    /**
     * 在指定组织下添加一个雇员
     * @param orgId 指定的组织
     * @param employee 要添加的雇员信息
     * @param subOrgIds 雇员的属的部门信息
     * @return 更新后的雇员信息
     */
    public static Employee createEmployeeForOrg(Long orgId,Employee employee, List<Long> subOrgIds){
        var createdEmployee = employee.createEmployeeForOrg(orgId);
        subOrgIds.forEach(it -> {
            var organization = Organization.queryOrganizationByOrgId(it);
            if(Objects.isNull(organization))throw new OrganizationNotExistsException(it);
            EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, organization);
        });
        return createdEmployee;
    }


    public static Employee updateEmployee(Employee employee,List<Long> subOrgIds,List<Long> roleIds){
        var updateEmployee = employee.updateEmployee();

        updateEmployeeOrgInfo(employee, subOrgIds);
        updateEmployeeRoleInfo(employee, roleIds);

        return updateEmployee;
    }

    private static void updateEmployeeRoleInfo(Employee employee, List<Long> roleIds) {
        var employeeRoles = EmployeeRoleAssignment.queryEmployeeRoles(employee.getId());
        var existsRoleIds = employeeRoles.stream().map(OrgRole::getId).collect(Collectors.toList());

        var needInsertRoleIds = roleIds.stream().filter(it -> !existsRoleIds.contains(it)).collect(Collectors.toList());
        var needRemoveRoleIds = existsRoleIds.stream().filter(it -> !roleIds.contains(it)).collect(Collectors.toList());
        needInsertRoleIds.forEach(it -> EmployeeRoleAssignment.assignEmployeeToRole(employee.getId(),it));
        needRemoveRoleIds.forEach(it -> EmployeeRoleAssignment.deAssignEmployeeFromRole(employee.getId(),it));
    }

    private static void updateEmployeeOrgInfo(Employee employee, List<Long> subOrgIds) {
        var organizations = EmployeeOrganizationRelation.queryEmployeeOrganizations(employee.getId());
        var existsOrgIds = organizations.stream().map(Organization::getId).collect(Collectors.toList());

        var needInsertOrgRelationIds = subOrgIds.stream().filter(it -> !existsOrgIds.contains(it)).collect(Collectors.toList());
        needInsertOrgRelationIds.forEach(it -> EmployeeOrganizationRelation.assignEmployeeToOrganization(employee.getId(), it));

        var needRemoveOrgRelationIds = existsOrgIds.stream().filter(it -> !subOrgIds.contains(it)).collect(Collectors.toList());
        needRemoveOrgRelationIds.forEach(it -> EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(employee.getId(), it));
    }


}
