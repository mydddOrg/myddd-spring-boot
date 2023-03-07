package org.myddd.java.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.java.AbstractTest;
import org.myddd.java.api.UserApplication;

import jakarta.inject.Inject;

public class UserApplicationImplTest extends AbstractTest {

    @Inject
    private UserApplication userApplication;

    @Test
    void createUser(){
        Assertions.assertNotNull(userApplication.createUser(randomUserDTO()));
    }

    @Test
    void searchUser(){
        Assertions.assertTrue(userApplication.searchUser(0,10,randomString()).getData().isEmpty());

        var created = userApplication.createUser(randomUserDTO());
        Assertions.assertFalse(userApplication.searchUser(0,10,created.getName()).getData().isEmpty());
    }
}
