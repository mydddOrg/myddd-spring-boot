package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.NotAllowedModifyThirdSourceException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.PhoneEmailCanNotAllEmptyException;
import org.myddd.extensions.security.api.UserApplication;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;

@Transactional
class EmployeeTest extends AbstractTest {

    @Inject
    private UserApplication userApplication;

    @Test
    void testCreateEmployee(){
        Employee createdEmployee = randomEmployee().createEmployee();
        Assertions.assertNotNull(createdEmployee);

        var emptyEmployee = new Employee();
        Assertions.assertThrows(RuntimeException.class,emptyEmployee::createEmployee);
    }

    @Test
    void testQueryEmployee(){
        Employee createdEmployee = randomEmployee().createEmployee();

        Employee query = Employee.queryEmployeeById(createdEmployee.getId());
        Assertions.assertNotNull(query);

        Employee notExists = Employee.queryEmployeeById(-1L);
        Assertions.assertNull(notExists);
    }

    @Test
    void testQueryEmployeeByUserIdAndOrgId(){
        Employee notExists = Employee.queryEmployeeByUserIdAndOrgId(randomLong(),-1L);
        Assertions.assertNull(notExists);

        Employee createdEmployee = randomEmployee().createEmployee();
        Employee queryEmployee = Employee.queryEmployeeByUserIdAndOrgId(createdEmployee.getUserId(),createdEmployee.getOrgId());
        Assertions.assertNotNull(queryEmployee);
    }

    @Test
    void testUpdateEmployee(){
        Employee createdEmployee = randomEmployee().createEmployee();
        Assertions.assertNotNull(createdEmployee);

        String email = randomId();
        createdEmployee.setEmail(email);

        Employee updated = createdEmployee.updateEmployee();
        Assertions.assertEquals(email,updated.getEmail());

        var thirdSourceEmployee = randomThirdSourceEmployee().createEmployee();
        Assertions.assertNotNull(thirdSourceEmployee);
        thirdSourceEmployee.setEmail(email);
        Assertions.assertThrowsExactly(NotAllowedModifyThirdSourceException.class, thirdSourceEmployee::updateEmployee);
    }

    @Test
    void testCreateEmployeeTypeEmployee(){
        var randomLong = randomLong();
        Assertions.assertThrowsExactly(OrganizationNotExistsException.class,()->Employee.queryOrCreateEmployeeByType(randomLong,EmployeeType.EMPLOYEE_OPEN_API));

        var createdOrganization = randomCompany().createTopCompany();
        var organizationId = createdOrganization.getId();
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->Employee.queryOrCreateEmployeeByType(organizationId,EmployeeType.EMPLOYEE_USER));

        var specialTypeEmployee = Employee.queryOrCreateEmployeeByType(createdOrganization.getId(),EmployeeType.EMPLOYEE_OPEN_API);
        Assertions.assertNotNull(specialTypeEmployee);
    }

    @Test
    void testImportFromExternal(){
        var randomEmployee = randomEmployee();
        var randomLong = randomLong();
        Assertions.assertThrows(OrganizationNotExistsException.class,()->randomEmployee.importFromExternal(randomLong));

        var topOrganization = randomCompany().createTopCompany();
        Assertions.assertNotNull(topOrganization);

        var notValidEmployee = randomEmployee();
        notValidEmployee.setEmail(null);
        notValidEmployee.setPhone(null);

        var topOrganizationId = topOrganization.getId();
        Assertions.assertThrows(PhoneEmailCanNotAllEmptyException.class,()->notValidEmployee.importFromExternal(topOrganizationId));

        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto());

        var emptyOrgFullPathEmployee = randomEmployee();
        randomEmployee().setOrgFullPath("");
        Assertions.assertDoesNotThrow(()->emptyOrgFullPathEmployee.importFromExternal(topOrganizationId));

        var orgFullPathEmployee = randomEmployee();
        var dept = randomId();
        var subDept = randomId();
        orgFullPathEmployee.setOrgFullPath(dept + "/" + subDept);
        Assertions.assertDoesNotThrow(()->orgFullPathEmployee.importFromExternal(topOrganizationId));
    }

}