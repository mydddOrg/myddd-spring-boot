package org.myddd.domain;

import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public class ApplicationTest {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationTest.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));
    }
}
