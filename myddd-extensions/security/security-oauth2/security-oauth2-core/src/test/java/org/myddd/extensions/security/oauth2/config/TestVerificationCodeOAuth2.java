package org.myddd.extensions.security.oauth2.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.oauth2.AbstractControllerTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TestVerificationCodeOAuth2 extends AbstractControllerTest {

    private DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");

    private String MOCK_CODE =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);

    @Test
    void testNotAuthorizationError(){
        ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl()+"/v1/mock/one",Object.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testRequestEmailToken(){
        TokenResponse tokenResponse = requestEmailCodeToken();
        Assertions.assertNotNull(tokenResponse);

        ResponseEntity<TokenResponse> response = requestToken(Map.of("grant_type","email_code","email",mockUsername,"validCode",randomId()));
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testRequestMobileToken(){
        TokenResponse tokenResponse = requestEmailCodeToken();
        Assertions.assertNotNull(tokenResponse);

        ResponseEntity<TokenResponse> response = requestToken(Map.of("grant_type","mobile_code","mobile",mockUsername,"validCode",randomId()));
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    protected TokenResponse requestEmailCodeToken(){
        ResponseEntity<TokenResponse> response = requestToken(Map.of("grant_type","mobile_code","mobile",mockUsername,"validCode",MOCK_CODE));
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        return response.getBody();
    }

    private ResponseEntity<TokenResponse> requestToken(Map<String,String> parameters){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(clientId,clientSecret);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("grant_type", parameters.get("grant_type"));
        body.add("email", parameters.get("email"));
        body.add("validCode", parameters.get("validCode"));

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(baseUrl()+"/oauth/token",requestEntity,TokenResponse.class);
    }
}
