package org.myddd.extensions.security.oauth2.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.oauth2.AbstractControllerTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestSecurityOauth2 extends AbstractControllerTest {

    @Test
    void testNotAuthorizationError(){
        ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl()+"/v1/mock/one",Object.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testRequestToken(){
        TokenResponse tokenResponse = requestToken();
        Assertions.assertNotNull(tokenResponse);
    }

    @Test
    void testRevokeToken(){
        TokenResponse tokenResponse = requestToken();
        ResponseEntity<Object> response = restTemplate.exchange(baseUrl()+"/v1/mock/one", HttpMethod.GET,createBearerHttpEntity(tokenResponse.getAccessToken()),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<Void> revokeTokenResponse = restTemplate.exchange(baseUrl()+ "/oauth/token/" + tokenResponse.getAccessToken(),HttpMethod.DELETE,createEmptyHttpEntity(),Void.class);
        Assertions.assertTrue(revokeTokenResponse.getStatusCode().is2xxSuccessful());

        response = restTemplate.exchange(baseUrl()+"/v1/mock/one", HttpMethod.GET,createBearerHttpEntity(tokenResponse.getAccessToken()),Object.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testAuthorization(){
        TokenResponse tokenResponse = requestToken();
        ResponseEntity<Object> response = restTemplate.exchange(baseUrl()+"/v1/mock/one", HttpMethod.GET,createBearerHttpEntity(tokenResponse.getAccessToken()),Object.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testRefreshToken(){
        TokenResponse tokenResponse = refreshToken();
        Assertions.assertNotNull(tokenResponse);
    }


    private TokenResponse refreshToken(){
        TokenResponse tokenResponse = requestToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(clientId,clientSecret);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", tokenResponse.getRefreshToken());

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(baseUrl()+"/oauth/token",requestEntity,TokenResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        return response.getBody();
    }


}
