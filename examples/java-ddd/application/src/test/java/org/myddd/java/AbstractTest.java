package org.myddd.java;

import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.java.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

@SpringBootTest(classes = AbstractTest.class)

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd","org.myddd.java"})
@EntityScan(basePackages = {"org.myddd","org.myddd.java"})
public abstract class AbstractTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomString(){
        return UUID.randomUUID().toString().replace("-","");
    }


    public UserDTO randomUserDTO(){
        var user = new UserDTO();
        user.setUserId(randomString());
        user.setName(randomString());
        user.setPhone(randomString());
        user.setEmail(randomString()+"@myddd.org");
        return user;
    }


}
