package org.myddd.extensions.security;

import io.grpc.netty.NettyServerBuilder;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.application.grpc.UserApplicationGrpcProxy;
import org.myddd.extensions.security.application.grpc.UserRoleApplicationGrpcProxy;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
public class Application {

    @Value("${grpc.port:8081}")
    private int grpcPort;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(ctx));


        var application = InstanceFactory.getInstance(Application.class);
        application.startGrpcService();
    }

    private void startGrpcService(){
        var server = NettyServerBuilder
                .forPort(grpcPort)
                .maxConnectionAge(10, TimeUnit.MINUTES)
                .maxConnectionAgeGrace(3,TimeUnit.MINUTES)
                .addService(new UserApplicationGrpcProxy())
                .addService(new UserRoleApplicationGrpcProxy())
                .build();

        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
