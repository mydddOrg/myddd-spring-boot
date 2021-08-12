package org.myddd.security.runner;

import org.myddd.security.api.LoginApplication;
import org.myddd.security.api.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.inject.Named;
import java.util.Timer;
import java.util.TimerTask;

@Named
public class LoginApplicationRunner implements ApplicationRunner {

    @Autowired
    private LoginApplication loginApplication;

    @Value("${super.username:admin}")
    private String superUsername;

    @Value("${super.password:admin123}")
    private String superPassword;

    @Override
    public void run(ApplicationArguments args) {
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (loginApplication.isEmpty()) {
                    LoginDTO loginDTO = new LoginDTO();
                    loginDTO.setUsername(superUsername);
                    loginDTO.setPassword(superPassword);
                    loginApplication.createSupper(loginDTO);
                }
            }
        }, 1000);
    }
}
