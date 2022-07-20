package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.EmployeeIdExistsException;
import org.myddd.extensions.organisation.OrgRoleNotExistsException;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.security.api.UserApplication;

import javax.transaction.Transactional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Transactional
class EmployeeServiceTest extends AbstractTest {

    @Mock
    private UserApplication userApplication;

    @Test
    void testCreateEmployeeForOrg(){

        var randomEmployee = randomEmployee();
        var randomLong = randomLong();
        List<Long> emptyList  = List.of();
        Assertions.assertThrows(OrganizationNotExistsException.class,()->EmployeeService.createEmployeeForOrg(randomLong,randomEmployee,emptyList));
        var topCompany = randomCompany().createTopCompany();

        Mockito.when(userApplication.createLocalUser(any())).thenReturn(userDtoFromEmployee(randomEmployee));
        var createdEmployee = EmployeeService.createEmployeeForOrg(topCompany.getId(),randomEmployee, List.of());
        Assertions.assertNotNull(createdEmployee);

        var someEmployeeIdEmployee = randomEmployee();
        someEmployeeIdEmployee.setEmployeeId(createdEmployee.getEmployeeId());
        var topCompanyId = topCompany.getId();
        Assertions.assertThrows(EmployeeIdExistsException.class,()->EmployeeService.createEmployeeForOrg(topCompanyId,someEmployeeIdEmployee,emptyList));

        List<Long> randomList = List.of(randomLong());
        var anotherRandomEmployee = randomEmployee();
        Assertions.assertThrows(OrganizationNotExistsException.class,()-> EmployeeService.createEmployeeForOrg(topCompanyId,anotherRandomEmployee,randomList));

        var createdSubOrg = randomDepartment(topCompany).createDepartment();
        Assertions.assertDoesNotThrow(()->EmployeeService.createEmployeeForOrg(topCompanyId,randomEmployee(),List.of(topCompanyId,createdSubOrg.getId())));
    }

    @Test
    void testUpdateEmployee(){
        var topCompany = randomCompany().createTopCompany();
        var createdSubOrg = randomDepartment(topCompany).createDepartment();

        var createdEmployee = EmployeeService.createEmployeeForOrg(topCompany.getId(),randomEmployee(),List.of(topCompany.getId(),createdSubOrg.getId()));

        var updateName = randomId();
        createdEmployee.setName(updateName);

        var role = randomOrgRoleWithCompany(topCompany).createRole();
        Assertions.assertDoesNotThrow(()->EmployeeService.updateEmployee(createdEmployee,List.of(topCompany.getId()),List.of(role.getId())));

        var randomList = List.of(randomLong());
        var roleList = List.of(role.getId());
        List<Long> emptyList = List.of();
        Assertions.assertThrows(OrganizationNotExistsException.class,()->EmployeeService.updateEmployee(createdEmployee,randomList,roleList));
        Assertions.assertThrows(OrgRoleNotExistsException.class,()->EmployeeService.updateEmployee(createdEmployee,emptyList,randomList));
    }

    private OrgRole randomOrgRoleWithCompany(Company company){
        OrgRole orgRole = new OrgRole();
        var orgRoleGroup = randomOrgRoleGroup(company).createRoleGroup();
        orgRole.setCompany(orgRoleGroup.getCompany());
        orgRole.setOrgRoleGroup(orgRoleGroup);
        orgRole.setName(randomId());
        orgRole.setCreator(randomLong());
        return orgRole;
    }
}
