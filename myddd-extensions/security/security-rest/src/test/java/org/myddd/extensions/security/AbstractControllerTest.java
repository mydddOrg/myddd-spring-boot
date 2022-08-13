package org.myddd.extensions.security;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myddd.domain.IDGenerate;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Inject
    private IDGenerate idGenerate;

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

    protected HttpEntity<Void> createEmptyHttpEntity(){
        return new HttpEntity<>(new HttpHeaders());
    }

    protected String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    protected Long randomLong(){
        return idGenerate.nextId();
    }

    protected String randomUser(){
        return randomUser(randomLong());
    }

    protected String randomRegisterUser(){
        DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
        var code =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("validCode",code);
        jsonObject.addProperty("password",randomId());
        jsonObject.addProperty("email",randomId() + "@foreverht.com");
        return jsonObject.toString();
    }

    protected String randomUser(Long userId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",userId);
        jsonObject.addProperty("name",randomId());
        jsonObject.addProperty("password",randomId());
        jsonObject.addProperty("email",randomId() + "@foreverht.com");
        jsonObject.addProperty("userId",randomId());
        return jsonObject.toString();
    }
}