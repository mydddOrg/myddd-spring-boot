package org.myddd.persistence;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.persistence.mock.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

@SpringBootTest(classes = ApplicationTest.class)
public abstract class AbstractTest extends ApplicationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    protected String randomId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    protected User randomUser(){
        User user = new User();
        user.setUserId(randomId());
        user.setPassword(randomId());
        user.setName(randomId());
        user.setEncodePassword(randomId());
        return user;
    }

}
