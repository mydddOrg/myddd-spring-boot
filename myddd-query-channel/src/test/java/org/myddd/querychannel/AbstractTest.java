package org.myddd.querychannel;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.domain.mock.User;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.test.TestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;


@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    protected static String randomId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    protected static User randomUser(){
        User user = new User();
        user.setUserId(randomId());
        user.setPassword(randomId());
        user.setName(randomId());
        user.setEncodePassword(randomId());
        return user;
    }

}
