package org.myddd.commons.sms.application;

import org.myddd.commons.verification.EmailVerificationCodeApplication;

import jakarta.inject.Named;
import org.myddd.domain.InstanceFactory;

import java.util.Objects;

@Named
public class EmailVerificationCodeApplicationImpl extends AbstractVerificationCodeApplication implements EmailVerificationCodeApplication {

    private static EmailGateway emailGateway;

    private static EmailGateway getEmailGateway(){
        if(Objects.isNull(emailGateway)){
            emailGateway = InstanceFactory.getInstance(EmailGateway.class);
        }
        return emailGateway;
    }
    @Override
    public void sendEmailCode(String email) {
        var nextCode = randomCode();
        cacheValidCode(email,nextCode);
        getEmailGateway().sendSmsToEmail(email,nextCode);
    }

    @Override
    public void validEmailCode(String email, String code) {
         validCode(email,code,emailGateway.isMock());
    }
}
