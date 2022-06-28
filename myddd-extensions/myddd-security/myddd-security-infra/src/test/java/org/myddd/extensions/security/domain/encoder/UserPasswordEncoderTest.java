package org.myddd.extensions.security.domain.encoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;
import org.myddd.extensions.security.domain.UserPasswordEncoder;

import javax.inject.Inject;

class UserPasswordEncoderTest extends AbstractTest {

    @Inject
    private UserPasswordEncoder passwordEncoder;

    private static final String PASSWORD = "hello world";

    @Test
    void testEncodePassword(){
        Assertions.assertNotNull(passwordEncoder.encodePassword(PASSWORD));
    }

}
