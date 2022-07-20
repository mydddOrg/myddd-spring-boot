package org.myddd.extensions.organisation.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.domain.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
class OrganizationRepositoryTest extends AbstractTest {

    @Inject
    private OrganizationRepository organizationRepository;

    @Test
    void testRepositoryExists(){
        Assertions.assertNotNull(organizationRepository);
    }

    @Test
    void createOrganization(){
        Organization organization = randomCompany();
        Organization created = organizationRepository.save(organization);
        Assertions.assertNotNull(created);
        Assertions.assertEquals(Company.class,created.getClass());

        var emptyCompany = new Company();
        Assertions.assertThrows(RuntimeException.class,()-> organizationRepository.save(emptyCompany));

        Organization errorOrganization = new Company();
        Assertions.assertThrows(RuntimeException.class,()-> organizationRepository.save(errorOrganization));
    }

    @Test
    void testQueryOrganizationById(){
        Organization notExistsOrganization = organizationRepository.queryOrganizationByOrgId(-1L);
        Assertions.assertNull(notExistsOrganization);

        Organization created = organizationRepository.save(randomCompany());
        Organization query = organizationRepository.queryOrganizationByOrgId(created.getId());
        Assertions.assertNotNull(query);
        Assertions.assertEquals(Company.class,query.getClass());
    }

    @Test
    void testBatchQueryOrganizations(){
        Set<Organization> organizationSet = organizationRepository.batchQueryOrganization(Set.of());
        Assertions.assertTrue(organizationSet.isEmpty());

        Organization created = organizationRepository.save(randomCompany());
        organizationSet = organizationRepository.batchQueryOrganization(Set.of(created.getId()));
        Assertions.assertFalse(organizationSet.isEmpty());
    }

    @Test
    void testQueryCompaniesByUserId(){
        List<Organization> emptyCompanies = organizationRepository.queryTopCompaniesByUserId(randomLong());
        Assertions.assertTrue(emptyCompanies.isEmpty());

        EmployeeOrganizationRelation employeeOrganizationRelation = assignEmployeeToOrganization();

        List<Organization> companies = organizationRepository.queryTopCompaniesByUserId(employeeOrganizationRelation.getEmployee().getUserId());
        Assertions.assertEquals(1,companies.size());
    }

    @Test
    void testUpdateOrganization(){
        Organization organization = randomCompany();
        Organization created = organizationRepository.save(organization);

        String newOrganizationName = randomId();
        created.setName(newOrganizationName);

        Organization updated = organizationRepository.updateOrganization(created);
        Assertions.assertEquals(newOrganizationName,updated.getName());
    }

    @Test
    void testQueryExistsOrganizationThirdIds(){
        Organization organization = randomCompany();
        var thirdSourceId = randomLong();
        organization.setThirdSourceId(thirdSourceId);
        organizationRepository.save(organization);

        var query = organizationRepository.queryExistsOrganizationsByThirdSourceId(thirdSourceId);
        Assertions.assertFalse(query.isEmpty());
    }

    @Test
    void testIsOrganizationEmpty(){
        var createdOrganization = randomCompany().createTopCompany();
        Assertions.assertTrue(organizationRepository.isOrganizationEmpty(createdOrganization.getId()));

        Employee createdEmployee = randomEmployee().createEmployee();
        EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, createdOrganization);
        Assertions.assertFalse(organizationRepository.isOrganizationEmpty(createdOrganization.getId()));
    }

    @Test
    void testQueryOrganizationByParentIdAndName(){
        var emptyQuery = organizationRepository.queryOrganizationByParentIdAndName(randomLong(),randomId());
        Assertions.assertNull(emptyQuery);

        var createdOrganization = randomCompany().createTopCompany();
        var subCreatedOrganization = randomDepartment(createdOrganization).createDepartment();
        var query = organizationRepository.queryOrganizationByParentIdAndName(createdOrganization.getId(),subCreatedOrganization.getName());
        Assertions.assertNotNull(query);
    }

    private EmployeeOrganizationRelation assignEmployeeToOrganization(){
        Organization createdOrganization = randomCompany().createTopCompany();
        Employee createdEmployee = randomEmployee(createdOrganization.getId()).createEmployee();

        return EmployeeOrganizationRelation.assignEmployeeToOrganization(createdEmployee, createdOrganization);
    }


}
