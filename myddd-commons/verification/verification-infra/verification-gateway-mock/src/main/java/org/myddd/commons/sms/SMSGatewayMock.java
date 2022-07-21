package org.myddd.commons.sms;

import org.myddd.commons.sms.application.SMSGateway;

import javax.inject.Named;

@Named
public class SMSGatewayMock implements SMSGateway {

    @Override
    public void sendSmsToMobile(String mobile, String code) {
        //这是一个MOCK实现，什么都不用做
    }

    @Override
    public boolean isMock() {
        return true;
    }
}
