package org.myddd.extensions.organisation.organization.auth;

import org.myddd.extensions.security.IAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class MockAuthentication {

    @Bean
    public IAuthentication authentication(){
        return mock(IAuthentication.class);
    }
}
