package org.myddd.domain;


import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.mock.Employee;
import org.myddd.domain.mock.Organization;
import org.myddd.domain.mock.User;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.test.TestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest{

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
        user.setName(randomId());
        return user;
    }

    protected Employee randomEmployee(){
        Employee employee = new Employee();
        employee.setName(randomId());
        return employee;
    }

    protected Organization randomOrganization(){
        Organization organization = new Organization();
        organization.setId(randomId());
        organization.setName(randomId());
        return organization;
    }

}
