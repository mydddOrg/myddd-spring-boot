package org.myddd.extensions.organisation.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.domain.EmployeeOrganizationRelation;
import org.myddd.extensions.organisation.domain.EmployeeRepository;
import org.myddd.extensions.organisation.domain.EmployeeRoleAssignment;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
class EmployeeRepositoryTest extends AbstractTest {


    @Inject
    private EmployeeRepository employeeRepository;

    @Test
    void testEmployeeRepositoryNotNull(){
        Assertions.assertNotNull(employeeRepository);
    }

    @Test
    void testQueryEmployeeByUserIdAndOrgId(){
        var created = employeeRepository.save(randomEmployee());
        Assertions.assertNotNull(created);

        var notExists = employeeRepository.queryEmployeeByUserIdAndOrgId(randomLong(),-1L);
        Assertions.assertNull(notExists);

        var exists = employeeRepository.queryEmployeeByUserIdAndOrgId(created.getUserId(),created.getOrgId());
        Assertions.assertNotNull(exists);
    }

    @Test
    void testIsEmployeeIdExistsInOrg(){
        var randomEmployee =  randomEmployee();
        Assertions.assertFalse(employeeRepository.isEmployeeIdExists(randomEmployee.getOrgId(),randomEmployee.getEmployeeId()));

        employeeRepository.save(randomEmployee);
        Assertions.assertTrue(employeeRepository.isEmployeeIdExists(randomEmployee.getOrgId(),randomEmployee.getEmployeeId()));
    }

    @Test
    void testBatchQueryEmployeeRoles(){
        var createdCompany = randomCompany().createTopCompany();
        var createdEmployee = randomEmployee(createdCompany.getId()).createEmployee();
        var createdRoleGroup = randomOrgRoleGroup(createdCompany).createRoleGroup();
        var createdRole = randomOrgRole(createdRoleGroup).createRole();

        Assertions.assertTrue(employeeRepository.batchQueryEmployeeRoles(List.of(createdEmployee.getId())).isEmpty());
        EmployeeRoleAssignment.assignEmployeeToRole(createdEmployee.getId(),createdRole.getId());
        Assertions.assertFalse(employeeRepository.batchQueryEmployeeRoles(List.of(createdEmployee.getId())).isEmpty());
    }

    @Test
    void testBatchClearEmployeeOrganization(){
        var createdCompany = randomCompany().createTopCompany();
        var createdEmployee = randomEmployee(createdCompany.getId()).createEmployee();

        var subDepartment = randomDepartment(createdCompany).createDepartment();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee.getId(),subDepartment.getId());

        employeeRepository.batchClearEmployeeOrganizations(List.of(createdEmployee.getId()));

        var emptyQuery = EmployeeOrganizationRelation.queryEmployeeOrganizations(createdEmployee.getId());
        Assertions.assertTrue(emptyQuery.isEmpty());
    }
}
