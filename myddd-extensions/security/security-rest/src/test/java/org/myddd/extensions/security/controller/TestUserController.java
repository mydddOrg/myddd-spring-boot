package org.myddd.extensions.security.controller;

import com.google.gson.JsonObject;
import org.myddd.extensions.security.AbstractControllerTest;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.utils.Page;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.Objects;

class TestUserController extends AbstractControllerTest {

    @Inject
    private IAuthentication authentication;

    @Test
    void testCreateUser(){
        Mockito.when(authentication.isAuthentication()).thenReturn(false);

        ResponseEntity<UserVO> errorResponse = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.POST,createHttpEntityFromString(randomUser()),UserVO.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        Mockito.when(authentication.loginUserId()).thenReturn(randomLong());

        errorResponse = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.POST,createHttpEntityFromString(noUserIdUser()),UserVO.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        UserVO created = createUser();
        Assertions.assertNotNull(created);

    }

    @Test
    void testQueryUser(){

        ResponseEntity<UserVO> errorResponse = restTemplate.exchange(baseUrl()+"/v1/users/-1", HttpMethod.GET,createEmptyHttpEntity(),UserVO.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        UserVO created = createUser();
        Assertions.assertNotNull(created);

        ResponseEntity<UserVO> responseEntity = restTemplate.exchange(baseUrl()+"/v1/users/"+created.getId(), HttpMethod.GET,createEmptyHttpEntity(),UserVO.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testQueryLoginUser(){
        var randomUserId = randomLong();
        UserVO created = createUser(randomUserId);
        Assertions.assertNotNull(created);

        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        Mockito.when(authentication.loginUserId()).thenReturn(created.getId());
        ResponseEntity<UserVO> responseEntity = restTemplate.exchange(baseUrl()+"/v1/current-login-user/user", HttpMethod.GET,createEmptyHttpEntity(),UserVO.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testPageQueryUser(){
        Mockito.when(authentication.isAuthentication()).thenReturn(false);

        ResponseEntity<Page<UserVO>> errorResponse = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.GET,createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        UserVO created = createUser();
        Assertions.assertNotNull(created);

        ResponseEntity<Page<UserVO>> response = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.GET,createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).getResultCount() > 0);
    }

    @Test
    void testEnableAndDisableUser(){
        ResponseEntity<Void> errorResponse = restTemplate.exchange(baseUrl()+"/v1/users/-1/enable",HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is4xxClientError());

        UserVO created = createUser();
        Assertions.assertNotNull(created);

        ResponseEntity<Void> disableUserResponse = restTemplate.exchange(baseUrl() + "/v1/users/" + created.getId() + "/disable",HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(disableUserResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<UserVO> queryUserResponse = restTemplate.exchange(baseUrl()+"/v1/users/"+created.getId(), HttpMethod.GET,createEmptyHttpEntity(),UserVO.class);
        Assertions.assertTrue(queryUserResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(queryUserResponse.getBody()).isDisabled());

        ResponseEntity<Void> enableUserResponse = restTemplate.exchange(baseUrl() + "/v1/users/" + created.getId() + "/enable",HttpMethod.PUT,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(enableUserResponse.getStatusCode().is2xxSuccessful());

        queryUserResponse = restTemplate.exchange(baseUrl()+"/v1/users/"+created.getId(), HttpMethod.GET,createEmptyHttpEntity(),UserVO.class);
        Assertions.assertTrue(queryUserResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(queryUserResponse.getBody()).isDisabled());

    }

    private UserVO createUser(){

        Mockito.when(authentication.isAuthentication()).thenReturn(true);
        Mockito.when(authentication.loginUserId()).thenReturn(randomLong());
        return createUser(randomLong());
    }

    private UserVO createUser(Long userId){
        Mockito.when(authentication.isAuthentication()).thenReturn(true);

        ResponseEntity<UserVO> responseEntity = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.POST,createHttpEntityFromString(randomUser(userId)),UserVO.class);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        return responseEntity.getBody();
    }

    private String noUserIdUser(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",randomId());
        jsonObject.addProperty("password",randomId());
        jsonObject.addProperty("email",randomId());
        return jsonObject.toString();
    }
}
