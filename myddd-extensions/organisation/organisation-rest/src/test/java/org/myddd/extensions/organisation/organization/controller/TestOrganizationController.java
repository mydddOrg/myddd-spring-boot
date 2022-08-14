package org.myddd.extensions.organisation.organization.controller;

import org.myddd.extensions.organisation.organization.AbstractControllerTest;
import org.myddd.extensions.organisation.organization.OrganizationVO;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.organization.api.EmployeeApplication;
import org.myddd.extensions.organization.api.OrganizationApplication;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.utils.Page;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class TestOrganizationController extends AbstractControllerTest {

    @Inject
    private IAuthentication authentication;

    @Inject
    private UserApplication userApplication;

    @Inject
    private OrganizationApplication organizationApplication;

    @Inject
    private EmployeeApplication employeeApplication;

    @Test
    void testNotAuthCreateOrganization(){
        when(authentication.isAuthentication()).thenReturn(false);

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is4xxClientError());
    }

    @Test
    void testQueryEmployeeDepartments(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        var createdOrganization = organizationApplication.createTopOrganization(randomOrganization());
        var createdDepartment = organizationApplication.createDepartment(randomSubOrganization(createdOrganization.getId()));
        organizationApplication.createDepartment(randomSubOrganization(createdDepartment.getId()));

        var createdEmployee = employeeApplication.createEmployee(randomEmployee(createdOrganization.getId()));

        var emptyResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + createdOrganization.getId() + "/employees/" + createdEmployee.getId() + "/departments",HttpMethod.GET,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(emptyResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testDeleteOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        var createdCompany = organizationApplication.createTopOrganization(randomOrganization());
        var response =  restTemplate.exchange(baseUrl() + "/v1/organizations/" + createdCompany.getId(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());

        var createdDepartment = organizationApplication.createDepartment(randomSubOrganization(createdCompany.getId()));
        response = restTemplate.exchange(baseUrl() + "/v1/organizations/" + createdDepartment.getId(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testListCompanyTree(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        var parentId = createResponse.getBody().getId();

        createResponse = restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateSubOrganization(parentId)),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        var listResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + parentId + "/tree",HttpMethod.GET,createEmptyHttpEntity(),OrganizationVO[].class);
        Assertions.assertTrue(listResponse.getStatusCode().is2xxSuccessful());
        var organizations = listResponse.getBody();
        Assertions.assertEquals(2, organizations.length);


        listResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + parentId + "/tree/query",HttpMethod.POST,createHttpEntityFromString(queryWithLimits(List.of(randomLong()))),OrganizationVO[].class);
        Assertions.assertTrue(listResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testPageSearchOrganizations(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        var parentId = createResponse.getBody().getId();

        createResponse = restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateSubOrganization(parentId)),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());


        ResponseEntity<Page<OrganizationVO>> searchResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + parentId + "/searcher",HttpMethod.GET,createEmptyHttpEntity(),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(searchResponse.getStatusCode().is2xxSuccessful());
        var organizations = searchResponse.getBody();
        Assertions.assertEquals(2, organizations.getData().size());

        searchResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + parentId + "/searcher?search="+ randomId(),HttpMethod.GET,createEmptyHttpEntity(),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(searchResponse.getStatusCode().is2xxSuccessful());
        organizations = searchResponse.getBody();
        Assertions.assertEquals(0, organizations.getData().size());

        searchResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + parentId + "/searcher/page-query",HttpMethod.POST,createHttpEntityFromString(pageQueryWithLimits(List.of(randomLong()))),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(searchResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(searchResponse.getBody().getData().isEmpty());
    }

    @Test
    void testUpdateOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());
        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        String newName = randomId();

        ResponseEntity<OrganizationVO> updateResponse =  restTemplate.exchange(baseUrl()+"/v1/organizations/" + createResponse.getBody().getId(),HttpMethod.PUT,createHttpEntityFromString(mockUpdateOrganizationJson(createResponse.getBody().getId(),newName)),OrganizationVO.class);
        Assertions.assertTrue(updateResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(newName,updateResponse.getBody().getName());
    }

    @Test
    void testCreateSubOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());

        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<OrganizationVO> createSubOrganizationResponse = restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateSubOrganization(createResponse.getBody().getId())),OrganizationVO.class);
        Assertions.assertTrue(createSubOrganizationResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testJoinOrganization(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());

        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> joinOrganizationResponse = restTemplate.exchange(baseUrl() + "/v1/organizations/" + createResponse.getBody().getId() + "/join", HttpMethod.PUT,createHttpEntityFromString(joinOrganizationBody(randomLong())),Void.class);
        Assertions.assertTrue(joinOrganizationResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testQueryTopCompaniesByUserId(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());

        UserDto randomUserDto = randomUserDto();
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<OrganizationVO[]> queryResponse = restTemplate.getForEntity(baseUrl()+"/v1/users/"+randomUserDto.getId()+"/organizations",OrganizationVO[].class);
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(queryResponse.getBody().length > 0);
    }

    @Test
    void testQueryCurrentLoginTopCompanies(){
        UserDto randomUserDto = randomUserDto("admin");
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto);

        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomUserDto.getId());

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<OrganizationVO[]> queryResponse = restTemplate.getForEntity(baseUrl()+"/v1/current-login-user/organizations",OrganizationVO[].class);
        Assertions.assertTrue(queryResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(queryResponse.getBody().length > 0);
    }

    @Test
    void testPageQueryOrganizations(){
        when(authentication.isAuthentication()).thenReturn(true);
        when(authentication.loginUserId()).thenReturn(randomLong());
        when(userApplication.queryUserById(any())).thenReturn(randomUserDto());

        ResponseEntity<OrganizationVO> createResponse =  restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateTopOrganization()),OrganizationVO.class);
        Assertions.assertTrue(createResponse.getStatusCode().is2xxSuccessful());


        ResponseEntity<Page<OrganizationVO>> responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createResponse.getBody().getId() + "/organizations", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(responseEntity.getBody().getData().isEmpty());

        ResponseEntity<OrganizationVO> createSubOrganizationResponse = restTemplate.postForEntity(baseUrl()+"/v1/organizations",createHttpEntityFromString(randomCreateSubOrganization(createResponse.getBody().getId())),OrganizationVO.class);
        Assertions.assertTrue(createSubOrganizationResponse.getStatusCode().is2xxSuccessful());
        responseEntity = restTemplate.exchange(baseUrl()+"/v1/organizations/" + createResponse.getBody().getId() + "/organizations", HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(responseEntity.getBody().getData().isEmpty());
    }

    private String joinOrganizationBody(Long userId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",userId);
        return jsonObject.toString();
    }

    private String mockUpdateOrganizationJson(long orgId,String name){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",orgId);
        jsonObject.addProperty("name",name);
        return jsonObject.toString();
    }

    private String randomCreateTopOrganization(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",randomId());
        return jsonObject.toString();
    }

    private String randomCreateSubOrganization(long parentId){
        JsonObject parent = new JsonObject();
        parent.addProperty("id",parentId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",randomId());
        jsonObject.add("parent",parent);

        return jsonObject.toString();
    }
}
