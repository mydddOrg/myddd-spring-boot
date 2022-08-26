package org.myddd.extensions.security.runner;

import com.google.protobuf.StringValue;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Timer;
import java.util.TimerTask;

@Named
public class InitAdminUserRunner implements ApplicationRunner {

    @Value("${oauth2.admin.username:admin}")
    private String adminUsername;

    @Value("${oauth2.admin.password:admin}")
    private String adminPassword;

    @Value("${oauth2.admin.email:admin@myddd.org}")
    private String adminEmail;

    @Inject
    private UserApplication userApplication;

    private Logger logger = LoggerFactory.getLogger(InitAdminUserRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                initAdminUser();
            }
        }, 1000);
    }

    private void initAdminUser(){
        var optionalUserDto = userApplication.queryLocalUserByUserId(StringValue.of(adminUsername));
        if(!optionalUserDto.hasUser()){
            UserDto admin = UserDto.newBuilder().setUserId(adminUsername).setPassword(adminPassword).setName(adminUsername).setEmail(adminEmail).build();
            userApplication.createLocalUser(admin);
            logger.debug("初始化admin成功");
        }
    }
}
