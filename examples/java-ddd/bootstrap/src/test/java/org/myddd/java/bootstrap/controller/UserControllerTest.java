package org.myddd.java.bootstrap.controller;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.java.api.dto.UserDTO;
import org.myddd.java.bootstrap.AbstractControllerTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    void createUser(){
        ResponseEntity<UserDTO> errorResponse = restTemplate.exchange(baseUrl()+"/v1/users", HttpMethod.POST,createHttpEntityFromString(randomUserJSON()),UserDTO.class);
        Assertions.assertTrue(errorResponse.getStatusCode().is2xxSuccessful());
    }


    private String randomUserJSON(){
        var jsonObject = new JsonObject();
        jsonObject.addProperty("userId",randomString());
        jsonObject.addProperty("name",randomString());
        jsonObject.addProperty("phone",randomString());
        jsonObject.addProperty("email",randomString());
        return jsonObject.toString();
    }
}
