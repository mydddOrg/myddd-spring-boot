package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.OrganizationEmployeeNotEmptyException;

import javax.transaction.Transactional;

@Transactional
class DepartmentTest extends AbstractTest {

    @Test
    void testCreateDepartment(){
        Department errorDepartment = randomDepartment(null);
        Assertions.assertThrows(RuntimeException.class,errorDepartment::createDepartment);

        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.isCompany());

        Department createdDepartment = randomDepartment(created).createDepartment();
        Assertions.assertNotNull(createdDepartment);
        Assertions.assertNotNull(createdDepartment.getFullPath());
        Assertions.assertEquals(createdDepartment.getRootParentId(),created.getId());
    }

    @Test
    void testUpdateDepartment(){
        Company created = randomCompany().createTopCompany();
        String newName = randomId();
        created.setName(newName);
        Organization updated = created.updateOrganization();
        Assertions.assertEquals(newName,updated.getName());
    }

    @Test
    void testDeleteDepartment(){
        Company topCompany = randomCompany().createTopCompany();
        var createdDepartment = randomDepartment(topCompany).createDepartment();

        Assertions.assertDoesNotThrow(()-> Organization.deleteOrganization(createdDepartment.getId()));

        var anotherDepartment = randomDepartment(topCompany).createDepartment();
        var createdEmployee = randomEmployee(topCompany.getId()).createEmployee();

        EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, anotherDepartment);

        var departmentId = anotherDepartment.getId();
        Assertions.assertThrows(OrganizationEmployeeNotEmptyException.class,()->Organization.deleteOrganization(departmentId));
    }
}
