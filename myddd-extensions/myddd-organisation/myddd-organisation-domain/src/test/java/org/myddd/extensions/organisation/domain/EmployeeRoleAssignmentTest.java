package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.*;

import javax.transaction.Transactional;

@Transactional
class EmployeeRoleAssignmentTest extends AbstractTest {

    @Test
    void testAssignEmployeeToRole(){
        Assertions.assertThrows(EmployeeNotExistsException.class,() -> EmployeeRoleAssignment.assignEmployeeToRole(-1L,-1L));

        Employee employee = randomEmployee().createEmployee();
        var employeeId = employee.getId();
        Assertions.assertThrows(OrgRoleNotExistsException.class,() -> EmployeeRoleAssignment.assignEmployeeToRole(employeeId,-1L));

        OrgRole orgRole = createRandomOrgRole();
        var orgRoleId = orgRole.getId();
        Assertions.assertThrows(EmployeeNotInCompanyException.class,()->EmployeeRoleAssignment.assignEmployeeToRole(employeeId,orgRoleId));

        Employee withCorrectOrgEmployee = randomEmployee(orgRole.getCompany().getId()).createEmployee();
        Assertions.assertDoesNotThrow(() -> EmployeeRoleAssignment.assignEmployeeToRole(withCorrectOrgEmployee.getId(),orgRole.getId()));

  }

    @Test
    void testDeAssignEmployeeFromRole(){
        Assertions.assertThrows(EmployeeNotInRoleException.class,() -> EmployeeRoleAssignment.deAssignEmployeeFromRole(-1L,-1L));

        OrgRole orgRole = createRandomOrgRole();
        Employee withCorrectOrgEmployee = randomEmployee(orgRole.getCompany().getId()).createEmployee();
        EmployeeRoleAssignment.assignEmployeeToRole(withCorrectOrgEmployee.getId(),orgRole.getId());

        Assertions.assertTrue(EmployeeRoleAssignment.deAssignEmployeeFromRole(withCorrectOrgEmployee.getId(),orgRole.getId()));
    }

    private OrgRole createRandomOrgRole(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);
        OrgRole orgRole = randomOrgRole();
        orgRole.setCompany(created);
        OrgRole createdRole = orgRole.createRole();
        Assertions.assertNotNull(createdRole);
        return createdRole;
    }
}
