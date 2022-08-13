package org.myddd.extensions.security.runner;

import org.myddd.extensions.security.AbstractControllerTest;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        sleep(2500);
        var optionalUserDto = userApplication.queryLocalUserByUserId(StringValue.of(adminEmail));
        Assertions.assertNotNull(optionalUserDto.hasUser());
    }


    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
