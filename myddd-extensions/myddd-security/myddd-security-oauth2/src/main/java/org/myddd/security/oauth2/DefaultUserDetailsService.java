package org.myddd.security.oauth2;


import org.myddd.security.api.LoginApplication;
import org.myddd.security.api.LoginDTO;
import org.myddd.utils.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
public class DefaultUserDetailsService implements UserDetailsService {

    @Inject
    private LoginApplication loginApplication;

    @Value("${auth.disable_security:false}")
    private boolean disableSecurity;

    @Value("${super.username:admin}")
    private String superUsername;

    @Value("${super.password:admin123}")
    private String superPassword;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return mockUser(username);
    }

    private UserDetails mockUser(String username) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


        if (disableSecurity){


            return User
                    .withUsername(superUsername)
                    .password(encoder.encode(superPassword))
                    .authorities(getAuthority())
                    .build();
        }

        LoginDTO loginDTO = loginApplication.queryLogin(username);


        Assert.notNull(loginDTO,"找不到此用户");

        Assert.isFalse(loginDTO.isDisabled(),"帐号已被禁用");

        return User
                .withUsername(loginDTO.getUsername())
                .password(encoder.encode(loginDTO.getPassword()))
                .authorities(getAuthority())
                .build();
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.emptyList();
    }
}