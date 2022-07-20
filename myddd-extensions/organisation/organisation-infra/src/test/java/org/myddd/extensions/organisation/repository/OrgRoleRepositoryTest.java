package org.myddd.extensions.organisation.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.domain.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
class OrgRoleRepositoryTest extends AbstractTest {

    @Inject
    private OrgRoleRepository repository;

    @Test
    void testRepositoryNotNull(){
        Assertions.assertNotNull(repository);
    }


    @Test
    void testQueryRolesByCompany(){
        List<OrgRole> emptyRoles = repository.queryRolesByCompany(-1L);
        Assertions.assertTrue(emptyRoles.isEmpty());

        OrgRole orgRole = createRandomOrgRole();

        List<OrgRole> roles = repository.queryRolesByCompany(orgRole.getCompany().getId());
        Assertions.assertFalse(roles.isEmpty());
    }

    @Test
    void testQueryEmployeeInRole(){
        Assertions.assertNull(repository.queryEmployeeInRole(-1L,-1L));

        OrgRole orgRole = createRandomOrgRole();
        Employee withCorrectOrgEmployee = randomEmployee(orgRole.getCompany().getId()).createEmployee();
        EmployeeRoleAssignment.assignEmployeeToRole(withCorrectOrgEmployee.getId(),orgRole.getId());

        Assertions.assertNotNull(repository.queryEmployeeInRole(withCorrectOrgEmployee.getId(),orgRole.getId()));
    }

    @Test
    void testQueryEmployeeRoles(){

        OrgRole orgRole = createRandomOrgRole();
        Employee employee = randomEmployee(orgRole.getCompany().getId()).createEmployee();
        Assertions.assertTrue(repository.queryEmployeeRoles(employee.getId()).isEmpty());

        EmployeeRoleAssignment.assignEmployeeToRole(employee.getId(),orgRole.getId());
        Assertions.assertFalse(repository.queryEmployeeRoles(employee.getId()).isEmpty());
    }

    @Test
    void testQueryOrgRoleGroupByCompany(){
        Assertions.assertTrue(repository.queryOrgRoleGroupByCompany(randomLong()).isEmpty());

        var company = randomCompany().createTopCompany();
        randomOrgRoleGroup(company).createRoleGroup();

        Assertions.assertTrue(repository.queryOrgRoleGroupByCompany(company.getId()).size() > 0);
    }

    @Test
    void testIsOrgRoleGroupEmpty(){
        var company = randomCompany().createTopCompany();
        var orgRoleGroup = randomOrgRoleGroup(company).createRoleGroup();

        Assertions.assertTrue(repository.isOrgRoleGroupEmpty(orgRoleGroup.getId()));

        OrgRole orgRole = randomOrgRole(orgRoleGroup);
        orgRole.setCompany(company);
        orgRole.createRole();

        Assertions.assertFalse(repository.isOrgRoleGroupEmpty(orgRoleGroup.getId()));
    }

    @Test
    void testIsOrgRoleEmpty(){
        var company = randomCompany().createTopCompany();
        var orgRoleGroup = randomOrgRoleGroup(company).createRoleGroup();

        OrgRole orgRole = randomOrgRole(orgRoleGroup);
        orgRole.setCompany(company);
        orgRole.createRole();
        Assertions.assertTrue(repository.isOrgRoleEmpty(orgRole.getId()));

        var employee = randomEmployee(company.getId()).createEmployee();
        EmployeeRoleAssignment.assignEmployeeToRole(employee.getId(),orgRole.getId());
        Assertions.assertFalse(repository.isOrgRoleEmpty(orgRole.getId()));
    }

    private OrgRole createRandomOrgRole(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);

        var orgRoleGroup = randomOrgRoleGroup(created).createRoleGroup();

        OrgRole orgRole = randomOrgRole(orgRoleGroup);
        orgRole.setCompany(created);
        OrgRole createdRole = orgRole.createRole();
        Assertions.assertNotNull(createdRole);
        return createdRole;
    }

}
