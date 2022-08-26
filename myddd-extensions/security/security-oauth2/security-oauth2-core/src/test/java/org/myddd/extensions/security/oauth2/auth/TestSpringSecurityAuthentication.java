package org.myddd.extensions.security.oauth2.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.oauth2.AbstractControllerTest;
import org.myddd.extensions.security.oauth2.config.TokenResponse;
import org.myddd.extensions.security.oauth2.controller.MockUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class TestSpringSecurityAuthentication extends AbstractControllerTest {


    @Test
    void testAuthorization(){
        TokenResponse tokenResponse = requestToken();
        ResponseEntity<Object> response = restTemplate.exchange(baseUrl()+"/v1/mock/auth", HttpMethod.GET,createBearerHttpEntity(tokenResponse.getAccessToken()),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetCurrentUserId(){
        TokenResponse tokenResponse = requestToken();
        ResponseEntity<MockUser> response = restTemplate.exchange(baseUrl()+"/v1/mock/userId", HttpMethod.GET,createBearerHttpEntity(tokenResponse.getAccessToken()),MockUser.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        Assertions.assertNotNull(response.getBody().getUserId());
    }
}
