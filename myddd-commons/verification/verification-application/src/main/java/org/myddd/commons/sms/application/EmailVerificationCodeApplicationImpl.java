package org.myddd.commons.sms.application;

import org.myddd.commons.verification.EmailVerificationCodeApplication;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class EmailVerificationCodeApplicationImpl extends AbstractVerificationCodeApplication implements EmailVerificationCodeApplication {

    @Inject
    private EmailGateway emailGateway;

    @Override
    public void sendEmailCode(String email) {
        var nextCode = randomCode();
        cacheValidCode(email,nextCode);
        emailGateway.sendSmsToEmail(email,nextCode);
    }

    @Override
    public void validEmailCode(String email, String code) {
         validCode(email,code,emailGateway.isMock());
    }
}
