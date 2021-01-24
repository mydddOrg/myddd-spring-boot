package cc.lingenliu.mhm.backend.document;

import cc.lingenliu.mhm.backend.document.starter.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractDocumentControllerTest {

    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Value("${local.server.port}")
    public int port;

    @Autowired
    public TestRestTemplate restTemplate;

    @Inject
    public ApplicationContext applicationContext;

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
}


