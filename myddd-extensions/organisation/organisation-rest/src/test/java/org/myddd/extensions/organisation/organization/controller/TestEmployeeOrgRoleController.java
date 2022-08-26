package org.myddd.extensions.organisation.organization.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.organisation.organization.AbstractControllerTest;
import org.myddd.extensions.organisation.organization.EmployeeVO;
import org.myddd.extensions.organisation.organization.OrgRoleGroupVO;
import org.myddd.extensions.organisation.organization.OrgRoleVO;
import org.myddd.extensions.organization.api.*;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.utils.Page;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TestEmployeeOrgRoleController extends AbstractControllerTest {

    @Inject
    private IAuthentication authentication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private OrgRoleApplication orgRoleApplication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Inject
    private UserApplication userApplication;

    @Test
    void testUpdateRoleGroup(){
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        var organization = organizationApplication.createTopOrganization(randomOrganization());
        var createJson = new JsonObject();
        createJson.addProperty("name",randomId());

        var response = restTemplate.exchange(baseUrl() + "/v1/organizations/" + organization.getId() + "/role-groups",HttpMethod.POST,createHttpEntityFromString(createJson.toString()),OrgRoleGroupVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        var updateBody = new JsonObject();
        updateBody.addProperty("name",randomId());
        var updateResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + organization.getId() + "/role-groups/" + Objects.requireNonNull(response.getBody()).getId(),HttpMethod.PUT,createHttpEntityFromString(updateBody.toString()),OrgRoleGroupVO.class);
        Assertions.assertTrue(updateResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateRoleGroup(){
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        var organization = organizationApplication.createTopOrganization(randomOrganization());
        var createJson = new JsonObject();
        createJson.addProperty("name",randomId());

        var response = restTemplate.exchange(baseUrl() + "/v1/organizations/" + organization.getId() + "/role-groups",HttpMethod.POST,createHttpEntityFromString(createJson.toString()),OrgRoleGroupVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateOrgRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(false);
        ResponseEntity<Void> unauthorizedResponse = restTemplate.postForEntity(baseUrl() + "/v1/organizations/1/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(randomLong())),Void.class);
        Assertions.assertEquals(401,unauthorizedResponse.getStatusCodeValue());

        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        ResponseEntity<Void> notValidResponse = restTemplate.postForEntity(baseUrl() + "/v1/organizations/1/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(randomLong())),Void.class);
        Assertions.assertEquals(400,notValidResponse.getStatusCodeValue());


        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        OrganizationDto topOrganization = organizationApplication.createTopOrganization(randomOrganization());
        Assertions.assertNotNull(topOrganization);

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(topOrganization.getId()));
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+topOrganization.getId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testListOrgRoles(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());

        var emptyResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + randomLong() +"/all-roles",HttpMethod.GET,createEmptyHttpEntity(), OrgRoleVO[].class);
        Assertions.assertTrue(emptyResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(0,Objects.requireNonNull(emptyResponse.getBody()).length);


        //创建一个OrgRole
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        OrganizationDto topOrganization = organizationApplication.createTopOrganization(randomOrganization());
        Assertions.assertNotNull(topOrganization);
        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(topOrganization.getId()));
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+topOrganization.getId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        var notEmptyResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + topOrganization.getId() +"/all-roles",HttpMethod.GET,createEmptyHttpEntity(),OrgRoleVO[].class);
        Assertions.assertTrue(notEmptyResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(notEmptyResponse.getBody()).length > 0);

        var emptyWithLimitsResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + topOrganization.getId() +"/all-roles/query",HttpMethod.POST,createHttpEntityFromString(queryWithLimits(List.of(randomLong()))),OrgRoleVO[].class);
        Assertions.assertTrue(emptyWithLimitsResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(0,Objects.requireNonNull(emptyWithLimitsResponse.getBody()).length);
    }

    @Test
    void testDeleteRoleGroup(){
        ResponseEntity<Void> errorResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/1/role-groups/" + randomLong(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        OrganizationDto topOrganization = organizationApplication.createTopOrganization(randomOrganization());
        Assertions.assertNotNull(topOrganization);
        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(topOrganization.getId()));

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/v1/organizations/"+orgRoleGroup.getOrganization().getId()+"/role-groups/" + orgRoleGroup.getId(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testUpdateRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        Mockito.when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        OrganizationDto created = organizationApplication.createTopOrganization(randomOrganization());

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(created.getId()));
        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+created.getId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        response = restTemplate.exchange(baseUrl() + "/v1/organizations/" + created.getId() + "/roles/" + Objects.requireNonNull(response.getBody()).getId(), HttpMethod.PUT,createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())), OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testAssignEmployeeToRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(createdEmployee.getId())
                        .setOrgId(createdEmployee.getOrgId())
                        .build()
        );

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdEmployee.getOrgId()));

        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+createdEmployee.getOrgId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> assignResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + createdEmployee.getId() + "/roles/" + response.getBody().getId(),HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(assignResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testBatchAssignToRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(createdEmployee.getId())
                        .setOrgId(createdEmployee.getOrgId())
                        .build()
        );

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdEmployee.getOrgId()));

        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+createdEmployee.getOrgId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> assignResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/"+orgRoleGroup.getOrganization().getId()+"/roles/assigner",HttpMethod.PUT,createHttpEntityFromString(batchAssignEmployeesToDto(List.of(createdEmployee.getId()),response.getBody().getId())),Void.class);

        Assertions.assertTrue(assignResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testBatchDeAssignEmployeeFromRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(createdEmployee.getId())
                        .setOrgId(createdEmployee.getOrgId())
                        .build()
        );

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdEmployee.getOrgId()));

        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+createdEmployee.getOrgId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> assignResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/"+orgRoleGroup.getOrganization().getId()+"/roles/assigner",HttpMethod.PUT,createHttpEntityFromString(batchAssignEmployeesToDto(List.of(createdEmployee.getId()),response.getBody().getId())),Void.class);
        Assertions.assertTrue(assignResponse.getStatusCode().is2xxSuccessful());


        ResponseEntity<Void> deAssignResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/"+orgRoleGroup.getOrganization().getId()+"/roles/de-assigner",HttpMethod.PUT,createHttpEntityFromString(batchAssignEmployeesToDto(List.of(createdEmployee.getId()),response.getBody().getId())),Void.class);
        Assertions.assertTrue(deAssignResponse.getStatusCode().is2xxSuccessful());

    }

    @Test
    void testDeAssignEmployeeFromRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(createdEmployee.getId())
                        .setOrgId(createdEmployee.getOrgId())
                        .build()
        );

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdEmployee.getOrgId()));

        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+createdEmployee.getOrgId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> assignResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + createdEmployee.getId() + "/roles/" + Objects.requireNonNull(response.getBody()).getId(),HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(assignResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> deAssignResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + createdEmployee.getId() + "/roles/" + response.getBody().getId(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(deAssignResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testPageQueryOrgRoles(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        ResponseEntity<Page<OrgRoleVO>> emptyDataResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/10/roles",HttpMethod.GET,createEmptyHttpEntity(),new ParameterizedTypeReference<>() {});

        Assertions.assertTrue(emptyDataResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(emptyDataResponse.getBody().getData().isEmpty());

        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        OrganizationDto created = organizationApplication.createTopOrganization(randomOrganization());
        Assertions.assertNotNull(created);

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(created.getId()));


        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+created.getId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        //check
        ResponseEntity<Page<OrgRoleVO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/organizations/"+created.getId()+"/roles",HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        //query by search
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/organizations/"+created.getId()+"/roles?search="+randomId(),HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());

        //search with limits
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/organizations/"+created.getId()+"/roles/page-query",HttpMethod.POST,createHttpEntityFromString(pageQueryWithLimits(List.of(randomLong()))),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().isEmpty());
    }

    @Test
    void testPageQueryRoleEmployees(){
        OrgRoleVO created = createRandomOrgRoleVO();
        //check
        ResponseEntity<Page<EmployeeVO>> queryResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + created.getCompany().getId() + "/roles/" + created.getId() + "/employees",HttpMethod.GET,createEmptyHttpEntity(),new ParameterizedTypeReference<>(){});
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(queryResponse.getBody()).getData().isEmpty());


        queryResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + created.getCompany().getId() + "/roles/" + created.getId() + "/employees/page-query",HttpMethod.POST,createHttpEntityFromString(pageQueryWithLimits(List.of(randomLong()))),new ParameterizedTypeReference<>(){});
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(queryResponse.getBody()).getData().isEmpty());
    }

    @Test
    void testQueryOrgRoleGroups(){
        var emptyResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/"  +randomLong() + "/role-groups",HttpMethod.GET,createEmptyHttpEntity(), OrgRoleGroupVO[].class);
        Assertions.assertTrue(emptyResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(0,Objects.requireNonNull(emptyResponse.getBody()).length);

        var createdOrgRoleVo = createRandomOrgRoleVO();

        var queryResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + createdOrgRoleVo.getCompany().getId() + "/role-groups",HttpMethod.GET,createEmptyHttpEntity(),OrgRoleGroupVO[].class);
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(queryResponse.getBody()).length  > 0);
    }

    private OrgRoleVO createRandomOrgRoleVO(){
        //prepared data
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        EmployeeDto createdEmployee = randomCreateEmployee();

        employeeApplication.assignEmployeeToOrganization(
                AssignEmployeeToOrganizationDto.newBuilder()
                        .setEmployeeId(createdEmployee.getId())
                        .setOrgId(createdEmployee.getOrgId())
                        .build()
        );

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(createdEmployee.getOrgId()));


        ResponseEntity<OrgRoleVO> response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+createdEmployee.getOrgId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> assignResponse = restTemplate.exchange(baseUrl() + "/v1/employees/" + createdEmployee.getId() + "/roles/" + Objects.requireNonNull(response.getBody()).getId(),HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(assignResponse.getStatusCode().is2xxSuccessful());

        return response.getBody();
    }

    @Test
    void testRemoveOrgRole(){
        var notValidResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + randomLong() + "/roles/" + randomLong(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(notValidResponse.getStatusCode().is4xxClientError());

        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());

        OrganizationDto topOrganization = organizationApplication.createTopOrganization(randomOrganization());
        Assertions.assertNotNull(topOrganization);

        var orgRoleGroup = orgRoleApplication.createOrgRoleGroup(randomOrgRoleGroup(topOrganization.getId()));
        var response = restTemplate.postForEntity(baseUrl() + "/v1/organizations/"+topOrganization.getId()+"/roles",createHttpEntityFromString(randomCreateOrgRoleJsonString(orgRoleGroup.getId())),OrgRoleVO.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        var validResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + topOrganization.getId() + "/roles/" + Objects.requireNonNull(response.getBody()).getId(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(validResponse.getStatusCode().is2xxSuccessful());

    }

    private String randomCreateOrgRoleJsonString(Long roleGroupId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",randomId());

        JsonObject roleGroup = new JsonObject();
        roleGroup.addProperty("id",roleGroupId);
        roleGroup.addProperty("name",randomId());
        jsonObject.add("roleGroup",roleGroup);
        return jsonObject.toString();
    }

    private EmployeeDto randomCreateEmployee(){
        var randomUserDto = randomUserDto();
        Mockito.when(userApplication.createLocalUser(any())).thenReturn(randomUserDto);
        Mockito.when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        EmployeeDto created = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));
        org.junit.jupiter.api.Assertions.assertNotNull(created);
        return created;
    }

    private String batchAssignEmployeesToDto(List<Long> employeeIds,Long roleId){
        var json = new JsonObject();
        json.addProperty("roleId",roleId);
        var jsonArray = new JsonArray();
        employeeIds.forEach(jsonArray::add);

        json.add("employeeIds",jsonArray);
        return json.toString();

    }
}
