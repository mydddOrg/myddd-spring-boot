package org.myddd.extensions.security;

import org.myddd.extensions.security.domain.UserPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BridgeUserPasswordEncoder implements UserPasswordEncoder {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
