package org.myddd.java.domain.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.java.domain.AbstractTest;
import org.myddd.java.domain.UserRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Transactional
public class UserRepositoryJPATest extends AbstractTest {

    @Inject
    private UserRepository userRepository;

    @Test
    void createUser(){
        var createdUser = userRepository.createUser(randomUser());
        Assertions.assertNotNull(createdUser);
    }

    @Test
    void searchUser(){
        Assertions.assertTrue(userRepository.searchUserByName(randomString()).isEmpty());

        var createdUser = userRepository.createUser(randomUser());
        Assertions.assertFalse(userRepository.searchUserByName(createdUser.getName()).isEmpty());
    }
}
