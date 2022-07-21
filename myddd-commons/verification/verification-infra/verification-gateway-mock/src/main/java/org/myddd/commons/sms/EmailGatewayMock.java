package org.myddd.commons.sms;

import org.myddd.commons.sms.application.EmailGateway;

import javax.inject.Named;

@Named
public class EmailGatewayMock implements EmailGateway {
    @Override
    public void sendSmsToEmail(String email, String code) {
        //do nothing
    }

    @Override
    public boolean isMock() {
        return true;
    }
}
