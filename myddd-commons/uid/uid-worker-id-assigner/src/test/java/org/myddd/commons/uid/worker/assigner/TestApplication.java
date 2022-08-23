package org.myddd.commons.uid.worker.assigner;

import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd","com.foreverht.lowcode"})
@EntityScan(basePackages = {"org.myddd","com.foreverht.lowcode"})
public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));
    }
}