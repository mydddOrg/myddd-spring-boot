package org.myddd.extensions.security;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.domain.Role;
import org.myddd.extensions.security.domain.User;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;
import java.util.UUID;


@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})

@SpringBootTest(classes = AbstractTest.class)
public abstract class AbstractTest{

    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    protected static String randomId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    protected static Long randomLong(){
        return new Random().nextLong();
    }

    protected static User randomUser(){
        User user = new User();
        user.setEmail(randomId() + "@taoofcoding.tech");
        user.setUserId(randomId());
        user.setPassword(randomId());
        user.setName(randomId());
        user.setEncodePassword(randomId());
        return user;
    }

    protected static Role randomRole(){
        Role role = new Role();
        role.setRoleId(randomId());
        role.setName(randomId());
        return role;
    }

}
