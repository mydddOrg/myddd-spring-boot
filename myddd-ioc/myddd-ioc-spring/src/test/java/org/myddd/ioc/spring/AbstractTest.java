package org.myddd.ioc.spring;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.domain.InstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = ApplicationTest.class)
public abstract class AbstractTest extends ApplicationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    protected InstanceProvider instanceProvider;

    @BeforeEach
    protected void beforeClass(){
        instanceProvider = SpringInstanceProvider.createInstance(applicationContext);
        InstanceFactory.setInstanceProvider(instanceProvider);
    }

}
