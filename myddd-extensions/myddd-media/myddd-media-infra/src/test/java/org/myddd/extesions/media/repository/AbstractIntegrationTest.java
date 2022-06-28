package org.myddd.extesions.media.repository;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

/**
 * 集成测试基类。
 * 
 * 
 * 
 */
@SpringBootTest(classes = ApplicationTest.class)
public abstract class AbstractIntegrationTest extends ApplicationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomId(){
        return UUID.randomUUID().toString();
    }
}
