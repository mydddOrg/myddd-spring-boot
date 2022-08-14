package org.myddd.extensions.organization;

import org.myddd.extensions.security.api.UserApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class SecurityConfigBean {

    @Bean
    UserApplication userApplication(){
        return mock(UserApplication.class);
    }
}
