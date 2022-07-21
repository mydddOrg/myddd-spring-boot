package org.myddd.extensions.security.oauth2.third;

import org.myddd.commons.verification.EmailVerificationCodeApplication;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.oauth2.GrantType;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;
import java.util.Objects;

public class EmailValidCodeTokenGranter extends AbstractLocalTokenGranter {

    private static final String GRANT_TYPE = GrantType.GRANT_TYPE_EMAIL_CODE;

    private static final String USERNAME_KEY = "email";


    private EmailVerificationCodeApplication emailVerificationCodeApplication;

    private EmailVerificationCodeApplication getEmailVerificationCodeApplication(){
        if(Objects.isNull(emailVerificationCodeApplication)){
            emailVerificationCodeApplication = InstanceFactory.getInstance(EmailVerificationCodeApplication.class);
        }
        return emailVerificationCodeApplication;
    }

    public EmailValidCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                                      ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    String getUsernameKey() {
        return USERNAME_KEY;
    }

    @Override
    void authentication(Map<String, String> parameters) {
        var email = parameters.get(USERNAME_KEY);
        var validCode = parameters.get("validCode");
        try {
            getEmailVerificationCodeApplication().validEmailCode(email,validCode);
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidGrantException(e.getMessage());
        }
    }
}
