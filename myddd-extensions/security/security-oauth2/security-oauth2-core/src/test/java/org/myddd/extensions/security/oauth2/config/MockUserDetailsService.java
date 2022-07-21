package org.myddd.extensions.security.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MockUserDetailsService implements UserDetailsService {

    @Value("${oauth2.mockUsername:admin}")
    private String mockUsername;

    @Value("${oauth2.mockPassword:admin}")
    private String mockPassword;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return new MockLoginUserInfo(mockUsername,passwordEncoder.encode(mockPassword));
    }
}