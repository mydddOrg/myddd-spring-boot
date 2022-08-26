package org.myddd.extensions.organisation.organization.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.organisation.organization.AbstractControllerTest;
import org.myddd.extensions.organisation.organization.EmployeeVO;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.utils.Page;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TestEmployeeController extends AbstractControllerTest {

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private IAuthentication authentication;

    @Inject
    private UserApplication userApplication;


    @Test
    void testCurrentLoginEmployeeInOrg(){

        var errorResponse = restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/-1/employee",HttpMethod.GET,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        var randomUserDto = randomUserDto();
        when(authentication.isAuthentication()).thenReturn(true);
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        OrganizationDto createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        var createdEmployee = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));

        when(authentication.loginUserId()).thenReturn(createdEmployee.getUserId());

        var response = restTemplate.exchange(baseUrl() + "/v1/current-login-user/organizations/"+createdOrganization.getId()+"/employee",HttpMethod.GET,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testAssignEmployeeToOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        ResponseEntity<Object> errorResponse = restTemplate.postForEntity(baseUrl()+"/v1/employeeAssigner",createHttpEntityFromString(buildAssignEmployeeVO(-1L,-1L)),Object.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        OrganizationDto createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        errorResponse = restTemplate.postForEntity(baseUrl()+"/v1/employeeAssigner",createHttpEntityFromString(buildAssignEmployeeVO(-1L,createdOrganization.getId())),Object.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        EmployeeDto createdEmployee = randomCreateEmployee();
        ResponseEntity<Object> response = restTemplate.postForEntity(baseUrl()+"/v1/employeeAssigner",createHttpEntityFromString(buildAssignEmployeeVO(createdEmployee.getId(),createdOrganization.getId())),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateEmployee(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        ResponseEntity<EmployeeVO> response = restTemplate.exchange(baseUrl()+"/v1/organizations/"+createdOrganization.getId()+"/employees",HttpMethod.POST,createHttpEntityFromString(buildCreateEmployeeJson()),EmployeeVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        var query = employeeApplication.pageQueryAllEmployeesInOrg(EmployeePageQueryDto.newBuilder().setPageSize(10).setOrgId(createdOrganization.getId()).build());
        Assertions.assertTrue(query.getTotal() > 0);
    }

    @Test
    void testUpdateEmployee(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        String newName = randomId();
        ResponseEntity<EmployeeVO> response = restTemplate.exchange(baseUrl()+"/v1/employees/" + createdEmployee.getId(),HttpMethod.PUT,createHttpEntityFromString(buildEmployeeJsonWithUpdate(createdEmployee,newName)),EmployeeVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(newName, Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void testQueryEmployee(){
        var notExistResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + randomLong(),HttpMethod.GET,createEmptyHttpEntity(),EmployeeVO.class);
        Assertions.assertTrue(notExistResponse.getStatusCode().is4xxClientError());

        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        var queryResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + createdEmployee.getId(),HttpMethod.GET,createEmptyHttpEntity(),EmployeeVO.class);
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
    }
    @Test
    void testPageQueryEmployeeByOrg(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        UserDto userDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(userDto);

        OrganizationDto createdOrganization = organizationApplication.createTopOrganization(randomOrganization());

        ResponseEntity<Page<EmployeeVO>> responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/employees", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        EmployeeDto createdEmployee = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        ResponseEntity<Object> response = restTemplate.postForEntity(baseUrl()+"/v1/employeeAssigner",createHttpEntityFromString(buildAssignEmployeeVO(createdEmployee.getId(),createdOrganization.getId())),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/employees", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        //page query with limits

        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/employees/page-query", HttpMethod.POST,createHttpEntityFromString(pageQueryWithLimits(List.of(createdEmployee.getId()))), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/employees/page-query", HttpMethod.POST,createHttpEntityFromString(pageQueryWithLimits(List.of(randomLong()))), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());
    }

    @Test
    void testPageQueryAllEmployeeInOrg(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        UserDto userDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(userDto);

        OrganizationDto createdOrganization = organizationApplication.createTopOrganization(randomOrganization());

        ResponseEntity<Page<EmployeeVO>> responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/all-employees", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        EmployeeDto createdEmployee = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        ResponseEntity<Object> response = restTemplate.postForEntity(baseUrl()+"/v1/employeeAssigner",createHttpEntityFromString(buildAssignEmployeeVO(createdEmployee.getId(),createdOrganization.getId())),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/all-employees", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        //query by search value
        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/all-employees?search=" + randomId(), HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        //query with limits
        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/all-employees/page-query", HttpMethod.POST,createHttpEntityFromString(employeePageQueryWithLimits(List.of(randomLong()))), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createdOrganization.getId() + "/all-employees/page-query", HttpMethod.POST,createHttpEntityFromString(employeePageQueryWithLimits(List.of(createdEmployee.getId()))), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());
    }

    @Test
    void testReAssignEmployeesToOrganizations(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        UserDto userDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(userDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        var createdEmployee = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        var subDepartment = organizationApplication.createDepartment(randomSubOrganization(createdOrganization.getId()));

        var response = restTemplate.exchange(baseUrl() + "/v1/organizations/"+createdOrganization.getId()+"/employees/re-assigner",HttpMethod.PUT, createHttpEntityFromString(reAssignEmployeeToOrganizationVO(List.of(createdEmployee.getId()),List.of(subDepartment.getId()))), Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    private String buildCreateEmployeeJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",randomId());
        jsonObject.addProperty("email",randomId());
        jsonObject.addProperty("phone",randomId());
        return jsonObject.toString();
    }

    private String buildEmployeeJsonWithUpdate(EmployeeDto dto,String name){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("email",dto.getEmail());
        jsonObject.addProperty("phone",dto.getPhone());
        return jsonObject.toString();
    }

    private String buildAssignEmployeeVO(long employeeId,long orgId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("employeeId",employeeId);
        jsonObject.addProperty("orgId",orgId);
        return jsonObject.toString();
    }

    private String reAssignEmployeeToOrganizationVO(List<Long> employeeIds, List<Long> organizations){
        var employeeArray = new JsonArray();
        employeeIds.forEach(employeeArray::add);

        var organizationArray = new JsonArray();
        organizations.forEach(organizationArray::add);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("employees",employeeArray);
        jsonObject.add("organizations",organizationArray);
        return jsonObject.toString();
    }

    private EmployeeDto randomCreateEmployee(){
        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        EmployeeDto created = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        Assertions.assertNotNull(created);
        return created;
    }



}
