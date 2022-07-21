package org.myddd.commons.sms;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.myddd.commons.AbstractTest;
import org.myddd.commons.sms.application.SMSGateway;
import org.myddd.commons.verification.MobileVerificationCodeApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class MobileVerificationCodeApplicationTest extends AbstractTest {


    @Inject
    private MobileVerificationCodeApplication verificationCodeApplication;

    @Inject
    private SMSGateway smsGateway;

    @BeforeEach
    void beforeEach(){
        Mockito.when(smsGateway.isMock()).thenReturn(true);
    }

    @Test
    void testValidCode(){
        var mobile = "18620501006";
        DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
        var code =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);
        var randomId = randomId();

        Assertions.assertThrows(InvalidVerificationCodeException.class,()->verificationCodeApplication.validCode(mobile,randomId));
        Assertions.assertDoesNotThrow(()->verificationCodeApplication.validCode(mobile,code));
    }
}
