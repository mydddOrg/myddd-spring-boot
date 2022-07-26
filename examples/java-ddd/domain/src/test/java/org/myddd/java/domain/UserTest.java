package org.myddd.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.java.AbstractTest;

import javax.transaction.Transactional;

public class UserTest extends AbstractTest {

    @Test
    @Transactional
    void createUser(){
       var user = randomUser().createUser();
        Assertions.assertNotNull(user);
    }
}
