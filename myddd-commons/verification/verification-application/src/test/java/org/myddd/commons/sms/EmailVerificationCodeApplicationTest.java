package org.myddd.commons.sms;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.myddd.commons.AbstractTest;
import org.myddd.commons.sms.application.EmailGateway;
import org.myddd.commons.verification.EmailVerificationCodeApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class EmailVerificationCodeApplicationTest extends AbstractTest {


    @Inject
    private EmailVerificationCodeApplication verificationCodeApplication;


    @Inject
    private EmailGateway emailGateway;

    @BeforeEach
    void beforeEach(){
        Mockito.when(emailGateway.isMock()).thenReturn(true);
    }

    @Test
    void sendEmailCode(){
        var email = "lingen.liu@gmail.com";
        Assertions.assertDoesNotThrow(() -> verificationCodeApplication.sendEmailCode(email));

        Mockito.when(emailGateway.isMock()).thenReturn(false);
        Mockito.doThrow(RuntimeException.class).when(emailGateway).sendSmsToEmail(anyString(),anyString());
        Assertions.assertThrows(RuntimeException.class,()->verificationCodeApplication.sendEmailCode(email));

        Mockito.when(emailGateway.isMock()).thenReturn(false);
        Mockito.doNothing().when(emailGateway).sendSmsToEmail(anyString(),anyString());
        Assertions.assertDoesNotThrow(() -> verificationCodeApplication.sendEmailCode(email));
    }

    @Test
    void testValidCode(){
        var email = "lingen.liu@gmail.com";
        DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
        var code =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);
        var randomId = randomId();
        Assertions.assertThrows(InvalidVerificationCodeException.class,()->verificationCodeApplication.validEmailCode(email,randomId));
        Assertions.assertDoesNotThrow(()->verificationCodeApplication.validEmailCode(email,code));

        Mockito.when(emailGateway.isMock()).thenReturn(false);
        Assertions.assertThrows(InvalidVerificationCodeException.class,()->verificationCodeApplication.validEmailCode(email,randomId));
    }

}
