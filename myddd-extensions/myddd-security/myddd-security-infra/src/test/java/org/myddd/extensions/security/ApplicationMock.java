package org.myddd.extensions.security;

import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public class ApplicationMock {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationMock.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));
    }
}
