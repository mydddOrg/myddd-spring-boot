package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.NotAllowedModifyThirdSourceException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
class EmployeeOrganizationRelationTest extends AbstractTest {

    @Inject
    private EmployeeRepository employeeRepository;

    @Test
    void testAssignEmployeeToOrganization(){
        EmployeeOrganizationRelation organizationRelation = assignEmployeeToOrganization();
        Assertions.assertNotNull(organizationRelation);

        var thirdSourceEmployee = randomThirdSourceEmployee().createEmployee();
        Assertions.assertThrowsExactly(NotAllowedModifyThirdSourceException.class,()-> EmployeeOrganizationRelation.assignEmployeeToOrganization(thirdSourceEmployee, organizationRelation.getOrganization()));
    }

    @Test
    void testQueryEmployeeOrganizations(){
        List<Organization> emptyOrganizations = EmployeeOrganizationRelation.queryEmployeeOrganizations(-1L);
        Assertions.assertTrue(emptyOrganizations.isEmpty());

        EmployeeOrganizationRelation organizationRelation = assignEmployeeToOrganization();
        List<Organization> organizationList = EmployeeOrganizationRelation.queryEmployeeOrganizations(organizationRelation.getEmployee().getId());
        Assertions.assertFalse(organizationList.isEmpty());
    }

    @Test
    void testQueryEmployeeAllOrganizations(){
        List<Organization> emptyOrganizations = EmployeeOrganizationRelation.queryEmployeeOrganizations(-1L);
        Assertions.assertTrue(emptyOrganizations.isEmpty());

        EmployeeOrganizationRelation organizationRelation = assignEmployeeToOrganization();
        List<Organization> organizationList = EmployeeOrganizationRelation.queryEmployeeAllOrganizations(organizationRelation.getEmployee().getId());
        Assertions.assertEquals(2,organizationList.size());
    }

    @Test
    void testDeAssignEmployeeFromOrganization(){
        EmployeeOrganizationRelation organizationRelation = assignEmployeeToOrganization();
        boolean success = EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(organizationRelation.getEmployee().getId(), organizationRelation.getOrganization().getId());
        Assertions.assertTrue(success);

        success = EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(organizationRelation.getEmployee().getId(), organizationRelation.getOrganization().getId());
        Assertions.assertFalse(success);

        success = EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(-1L, -1L);
        Assertions.assertFalse(success);

        var thirdSourceEmployeeOrganizationRelation = mockThirdSourceEmployeeToOrganization();
        Assertions.assertThrowsExactly(NotAllowedModifyThirdSourceException.class,()->EmployeeOrganizationRelation.deAssignEmployeeFromOrganization(thirdSourceEmployeeOrganizationRelation.getEmployee().getId(), thirdSourceEmployeeOrganizationRelation.getOrganization().getId()));
    }

    @Test
    void testReAssignEmployeeListToOrganization(){
        var createdCompany = randomCompany().createTopCompany();
        var createdEmployee = randomEmployee(createdCompany.getId()).createEmployee();

        var subDepartment = randomDepartment(createdCompany).createDepartment();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee.getId(),subDepartment.getId());

        var anotherSubDepartment = randomDepartment(createdCompany).createDepartment();

        EmployeeOrganizationRelation.reAssignEmployeeListToOrganization(List.of(createdEmployee.getId()),List.of(anotherSubDepartment.getId(),subDepartment.getId()));
        var query = EmployeeOrganizationRelation.queryEmployeeOrganizations(createdEmployee.getId());
        Assertions.assertEquals(2,query.size());


        EmployeeOrganizationRelation.reAssignEmployeeListToOrganization(List.of(createdEmployee.getId()),List.of(anotherSubDepartment.getId()));
        query = EmployeeOrganizationRelation.queryEmployeeOrganizations(createdEmployee.getId());
        Assertions.assertEquals(1,query.size());
    }

    private EmployeeOrganizationRelation assignEmployeeToOrganization(){
        Company createdOrganization = randomCompany().createTopCompany();
        Organization subOrganization = randomDepartment(createdOrganization).createDepartment();
        Employee createdEmployee = randomEmployee().createEmployee();

        return EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, subOrganization);
    }

    private EmployeeOrganizationRelation mockThirdSourceEmployeeToOrganization(){
        Company createdOrganization = randomCompany().createTopCompany();
        Organization subOrganization = randomDepartment(createdOrganization).createDepartment();
        Employee createdEmployee = randomThirdSourceEmployee().createEmployee();

        EmployeeOrganizationRelation employeeOrganizationRelation = new EmployeeOrganizationRelation();
        employeeOrganizationRelation.setEmployee(createdEmployee);
        employeeOrganizationRelation.setOrganization(subOrganization);

        return employeeRepository.save(employeeOrganizationRelation);
    }
}
