package org.myddd.extensions.organisation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.AbstractTest;
import org.myddd.extensions.organisation.OrganizationNotExistsException;
import org.myddd.extensions.organisation.UserNotFoundException;
import org.myddd.extensions.organisation.UserNotFoundInOrgException;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
class OrganizationServiceTest extends AbstractTest {


    @Inject
    private OrganizationService organizationService;

    @Inject
    private UserApplication userApplication;

    @Test
    void testOrganizationServiceNotNull(){
        Assertions.assertNotNull(organizationService);
    }

    @Test
    void testCreateTopOrganization(){
        when(userApplication.queryUserById(any())).thenReturn(null);

        var randomCompany = randomCompany();
        Assertions.assertThrows(UserNotFoundException.class,()->{
            organizationService.createTopOrganization(randomCompany);
        });


        UserDto random = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(random);
        Organization createdOrganization = organizationService.createTopOrganization(randomCompany(random.getUserId()));
        Assertions.assertNotNull(createdOrganization);
    }

    @Test
    void testJoinOrganization(){
        when(userApplication.queryUserById(any())).thenReturn(null);
        var randomLong = randomLong();
        Assertions.assertThrows(UserNotFoundException.class,()->{
            organizationService.joinOrganization(randomLong,-1);
        });

        UserDto randomUserDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);
        var userId = randomUserDto.getId();
        Assertions.assertThrows(OrganizationNotExistsException.class,()->{
            organizationService.joinOrganization(userId,-1);
        });

        Organization createdOrganization = organizationService.createTopOrganization(randomCompany(randomUserDto.getUserId()));

        var employee = Employee.queryEmployeeByUserIdAndOrgId(randomUserDto.getId(),createdOrganization.getId());
        Assertions.assertNotNull(employee);

        Assertions.assertDoesNotThrow(() -> organizationService.joinOrganization(employee.getId(),createdOrganization.getId()));
    }

    @Test
    void testLeaveOrganization(){

        var randomLong = randomLong();
        Assertions.assertThrows(UserNotFoundInOrgException.class,()->{
            organizationService.leaveOrganization(randomLong,-1L);
        });

        UserDto randomUserDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);
        Organization createdOrganization = organizationService.createTopOrganization(randomCompany(randomUserDto.getUserId()));

        boolean success = organizationService.leaveOrganization(randomUserDto.getId(),createdOrganization.getId());
        Assertions.assertTrue(success);
    }

}
