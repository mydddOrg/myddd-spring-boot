package org.myddd.extensions.security.oauth2;

import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.oauth2.auth.SpringSecurityAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAuthenticationBean {

    @Bean
    public IAuthentication authentication(){
        return new SpringSecurityAuthentication();
    }
}
