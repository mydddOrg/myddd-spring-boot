package org.myddd.extensions.security;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfigBean {

    @Bean
    public IAuthentication authentication(){
        return Mockito.mock(IAuthentication.class);
    }
}
