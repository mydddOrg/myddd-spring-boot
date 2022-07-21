package org.myddd.extensions.security.oauth2.auth;


import org.myddd.extensions.security.IAuthentication;

import java.util.Random;

public class MockAuthentication implements IAuthentication {


    public static final Long mockUserId = Math.abs(new Random().nextLong());

    @Override
    public boolean isAuthentication() {
        return true;
    }

    @Override
    public Long loginUserId() {
        return mockUserId;
    }
}
