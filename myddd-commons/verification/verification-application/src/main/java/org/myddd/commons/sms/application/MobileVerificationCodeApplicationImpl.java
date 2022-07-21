package org.myddd.commons.sms.application;

import org.myddd.commons.verification.MobileVerificationCodeApplication;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MobileVerificationCodeApplicationImpl extends AbstractVerificationCodeApplication implements MobileVerificationCodeApplication {

    @Inject
    private SMSGateway smsGateway;

    @Override
    public void sendCode(String mobile) {
        var nextCode = randomCode();
        cacheValidCode(mobile,nextCode);
        smsGateway.sendSmsToMobile(mobile,nextCode);
    }

    @Override
    public void validCode(String mobile, String code) {
        validCode(mobile,code,smsGateway.isMock());
    }
}
