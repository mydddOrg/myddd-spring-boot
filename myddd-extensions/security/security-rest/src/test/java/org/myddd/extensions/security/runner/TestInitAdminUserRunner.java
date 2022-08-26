package org.myddd.extensions.security.runner;

import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractControllerTest;
import org.myddd.extensions.security.api.UserApplication;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;

class TestInitAdminUserRunner extends AbstractControllerTest {

    @Value("${oauth2.admin.email:admin@myddd.org}")
    private String adminEmail;

    @Inject
    private UserApplication userApplication;

    @Test
    void testAdminUserExists(){
        sleep();
        var optionalUserDto = userApplication.queryLocalUserByUserId(StringValue.of(adminEmail));
        Assertions.assertTrue(optionalUserDto.hasUser());
    }


    private void sleep(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
