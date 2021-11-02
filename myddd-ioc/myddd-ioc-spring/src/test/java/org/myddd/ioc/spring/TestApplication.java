package org.myddd.ioc.spring;

import org.myddd.domain.InstanceFactory;
import org.myddd.domain.InstanceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public class TestApplication {

    private static InstanceProvider instanceProvider;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));
    }
}
