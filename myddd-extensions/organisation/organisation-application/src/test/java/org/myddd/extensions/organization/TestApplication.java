package org.myddd.extensions.organization;

import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));
    }
}
