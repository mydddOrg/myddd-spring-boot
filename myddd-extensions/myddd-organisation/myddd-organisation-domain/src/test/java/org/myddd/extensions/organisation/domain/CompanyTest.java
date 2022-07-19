package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.NotAllowedDeleteTopOrganizationException;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
class CompanyTest extends AbstractTest {

    @Test
    void testCreateTopCompany(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.isCompany());
        Assertions.assertEquals(created.getId(),created.getRootParentId());

        Company subCompany = new Company();
        subCompany.setParent(created);
        Assertions.assertThrows(RuntimeException.class, subCompany::createTopCompany);
    }

    @Test
    void testCreateSubCompany(){
        Company created = randomCompany().createTopCompany();
        Assertions.assertNotNull(created);
        Assertions.assertTrue(created.isCompany());

        Company subCompany = randomCompany();
        subCompany.setParent(created);
        Company createdSubCompany = subCompany.createSubCompany();
        Assertions.assertNotNull(createdSubCompany);
        Assertions.assertEquals(createdSubCompany.getRootParentId(),created.getId());

        Company errorSubCompany = randomCompany();
        Assertions.assertThrows(RuntimeException.class, errorSubCompany::createSubCompany);
    }

    @Test
    void testQueryTopCompaniesByUserId(){
        List<Organization> emptyOrganizationList = Company.queryTopCompaniesByUserId(randomLong());
        Assertions.assertTrue(emptyOrganizationList.isEmpty());

        Organization createdOrganization = randomCompany().createTopCompany();
        Employee createdEmployee = randomEmployee(createdOrganization.getId()).createEmployee();

        List<Organization> organizationList = Company.queryTopCompaniesByUserId(createdEmployee.getUserId());
        Assertions.assertFalse(organizationList.isEmpty());
    }

    @Test
    void testDeleteCompany(){
        Organization createdOrganization = randomCompany().createTopCompany();

        var organizationId = createdOrganization.getId();
        Assertions.assertThrows(NotAllowedDeleteTopOrganizationException.class,()-> Organization.deleteOrganization(organizationId));
    }

    private EmployeeOrganizationRelation assignEmployeeToOrganization(){
        Organization createdOrganization = randomCompany().createTopCompany();
        Employee createdEmployee = randomEmployee().createEmployee();

        return EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, createdOrganization);
    }
}
