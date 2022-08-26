package org.myddd.extensions.organisation.organization.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organisation.organization.AbstractControllerTest;
import org.myddd.extensions.organisation.organization.DynamicResultVO;
import org.myddd.extensions.organization.api.EmployeeApplication;
import org.myddd.extensions.organization.api.OrganizationApplication;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.api.UserApplication;
import org.springframework.http.HttpMethod;

import javax.inject.Inject;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TestCurrentLoginController extends AbstractControllerTest {

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private IAuthentication authentication;

    @Inject
    private UserApplication userApplication;

    @Test
    void testCurrentEmployee(){
        var randomUserDto = randomUserDto();
        when(authentication.isAuthentication()).thenReturn(true);
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);
        when(authentication.loginUserId()).thenReturn(randomUserDto.getId());

        var createdCompany = organizationApplication.createTopOrganization(randomOrganizationWithUserId(randomUserDto.getId()));
        var response = restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/"+createdCompany.getId()+"/dynamic/my-employee", HttpMethod.GET,createEmptyHttpEntity(), DynamicResultVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(response.getBody()).getEmployees().isEmpty());

        organizationApplication.createDepartment(randomSubOrganization(createdCompany.getId()));

        response =  restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/"+createdCompany.getId()+"/dynamic/my-departments", HttpMethod.GET,createEmptyHttpEntity(), DynamicResultVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(1,Objects.requireNonNull(response.getBody()).getOrganizations().size());

        response =  restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/"+createdCompany.getId()+"/dynamic/my-departments-include-children", HttpMethod.GET,createEmptyHttpEntity(), DynamicResultVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(2,Objects.requireNonNull(response.getBody()).getOrganizations().size());

        var errorResponse = restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/"+createdCompany.getId()+"/dynamic/not-exists-key", HttpMethod.GET,createEmptyHttpEntity(), DynamicResultVO.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());
    }
}
