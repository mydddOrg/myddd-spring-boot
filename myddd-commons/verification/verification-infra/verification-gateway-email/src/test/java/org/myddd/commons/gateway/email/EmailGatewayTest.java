package org.myddd.commons.gateway.email;

import org.myddd.commons.gateway.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.myddd.commons.sms.application.EmailGateway;

import javax.inject.Inject;

public class EmailGatewayTest extends AbstractTest{


    @Inject
    private EmailGateway emailGateway;

    @Test
    @Disabled("会发送真实的邮件")
    void testSendEmail(){
        Assertions.assertNotNull(emailGateway);
        Assertions.assertDoesNotThrow(()->emailGateway.sendSmsToEmail("liulin@foreverht.com",randomId()));
    }

}
