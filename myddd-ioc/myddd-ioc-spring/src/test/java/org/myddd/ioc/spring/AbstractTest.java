package org.myddd.ioc.spring;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.domain.InstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = AbstractTest.class)
@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public abstract class AbstractTest {

    @Autowired
    protected ApplicationContext applicationContext;

    protected InstanceProvider instanceProvider;

    @BeforeEach
    protected void beforeClass(){
        instanceProvider = SpringInstanceProvider.createInstance(applicationContext);
        InstanceFactory.setInstanceProvider(instanceProvider);
    }

}
