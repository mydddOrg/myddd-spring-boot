package org.myddd.extesions.media;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.util.UUID;


@SpringBootTest(classes = AbstractTest.class)
@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
@ImportResource({"classpath:META-INF/*.xml"})
public abstract class AbstractTest{

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
