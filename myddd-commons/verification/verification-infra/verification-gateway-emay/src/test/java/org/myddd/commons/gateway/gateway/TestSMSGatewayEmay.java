package org.myddd.commons.gateway.gateway;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.myddd.commons.gateway.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.sms.application.SMSGateway;

public class TestSMSGatewayEmay extends AbstractTest {

    @Inject
    private SMSGateway smsGateway;


    @Test
    void testSMSGatewayNotNull(){
        Assertions.assertNotNull(smsGateway);
    }


    @Test
    @Disabled("会触发真实的网关调用")
    void testSendSMS(){
        var mobile = "18620501006";
        var code = randomId();

        Assertions.assertDoesNotThrow(()->smsGateway.sendSmsToMobile(mobile,code));
    }

}
