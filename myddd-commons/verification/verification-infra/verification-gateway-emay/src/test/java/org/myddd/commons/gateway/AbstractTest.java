package org.myddd.commons.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Random;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest extends TestApplication {

    private static Random random = new Random();

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    protected Long randomLong(){
        return random.nextLong();
    }
}
