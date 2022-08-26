package org.myddd.extensions.security.oauth2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.oauth2.config.TokenResponse;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {

    @Value("${server.servlet.context-path:/}")
    public String contextPath;

    @Value("${local.server.port}")
    public int port;

    @Autowired
    public TestRestTemplate restTemplate;

    @Inject
    public ApplicationContext applicationContext;


    @Value("${oauth2.clientId:admin}")
    protected String clientId;

    @Value("${oauth2.clientSecret:admin}")
    protected String clientSecret;

    @Value("${oauth2.mockUsername:admin}")
    protected String mockUsername;

    @Value("${oauth2.mockPassword:admin}")
    protected String mockPassword;


    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String baseUrl(){
        return "http://localhost:"+port+contextPath;
    }

    protected HttpEntity<String> createHttpEntityFromString(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return entity;
    }

    protected HttpEntity createEmptyHttpEntity(){
        return new HttpEntity<>(new HttpHeaders());
    }

    protected HttpEntity createBearerHttpEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(headers);

        return entity;
    }

    protected TokenResponse requestToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(clientId,clientSecret);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", mockUsername);
        body.add("password", mockPassword);


        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(baseUrl()+"/oauth/token",requestEntity,TokenResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        return response.getBody();
    }

    protected String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}


