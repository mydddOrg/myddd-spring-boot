package org.myddd.extensions.security.controller;

import org.myddd.extensions.security.AbstractControllerTest;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.myddd.extensions.security.IAuthentication;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;

class TestUserRoleController extends AbstractControllerTest {

    @Inject
    private IAuthentication authentication;

    @Test
    void testCreateRole(){
        Mockito.when(authentication.isAuthentication()).thenReturn(false);
        Mockito.when(authentication.loginUserId()).thenReturn(randomLong());
        ResponseEntity<Void> errorResponse = restTemplate.postForEntity(baseUrl()+"/v1/roles",createHttpEntityFromString(randomRoleJson()),Void.class);
        Assertions.assertEquals(401,errorResponse.getStatusCodeValue());
    }

    private String randomRoleJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("roleId",randomId());
        jsonObject.addProperty("name",randomId());
        return jsonObject.toString();
    }
}
