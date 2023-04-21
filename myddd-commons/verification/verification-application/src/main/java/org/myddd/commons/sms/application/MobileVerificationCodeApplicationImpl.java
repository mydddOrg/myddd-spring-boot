package org.myddd.commons.sms.application;

import org.myddd.commons.verification.MobileVerificationCodeApplication;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.myddd.domain.InstanceFactory;

import java.util.Objects;

@Named
public class MobileVerificationCodeApplicationImpl extends AbstractVerificationCodeApplication implements MobileVerificationCodeApplication {

    private static SMSGateway smsGateway;

    private static SMSGateway getSmsGateway(){
        if(Objects.isNull(smsGateway)){
            smsGateway = InstanceFactory.getInstance(SMSGateway.class);
        }
        return smsGateway;
    }

    @Override
    public void sendCode(String mobile) {
        var nextCode = randomCode();
        cacheValidCode(mobile,nextCode);
        getSmsGateway().sendSmsToMobile(mobile,nextCode);
    }

    @Override
    public void validCode(String mobile, String code) {
        validCode(mobile,code,getSmsGateway().isMock());
    }
}
