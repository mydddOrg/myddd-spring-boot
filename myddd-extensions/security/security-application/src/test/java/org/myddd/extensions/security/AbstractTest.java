package org.myddd.extensions.security;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

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

    protected String randomId(){
        return UUID.randomUUID().toString();
    }

    protected UserDto randomUserDto(){
        return UserDto.newBuilder()
                .setUserId(randomId())
                .setEmail(randomId() + "@taoofcoding.tech")
                .setPassword(randomId())
                .setName(randomId())
                .build();
    }

    protected RoleDto randomRoleDto(){
        return RoleDto.newBuilder()
                .setName(randomId())
                .setRoleId(randomId())
                .build();
    }
}
