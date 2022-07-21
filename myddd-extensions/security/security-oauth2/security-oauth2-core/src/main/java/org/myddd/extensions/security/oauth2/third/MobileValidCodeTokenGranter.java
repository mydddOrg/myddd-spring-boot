package org.myddd.extensions.security.oauth2.third;

import org.myddd.commons.verification.MobileVerificationCodeApplication;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.oauth2.GrantType;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;
import java.util.Objects;

public class MobileValidCodeTokenGranter extends AbstractLocalTokenGranter{

    private static final String GRANT_TYPE = GrantType.GRANT_TYPE_MOBILE_CODE;

    private static final String USERNAME_KEY = "mobile";

    private MobileVerificationCodeApplication mobileVerificationCodeApplication;

    private MobileVerificationCodeApplication getMobileVerificationCodeApplication(){
        if(Objects.isNull(mobileVerificationCodeApplication)){
            mobileVerificationCodeApplication = InstanceFactory.getInstance(MobileVerificationCodeApplication.class);
        }
        return mobileVerificationCodeApplication;
    }

    public MobileValidCodeTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    String getUsernameKey() {
        return USERNAME_KEY;
    }

    @Override
    void authentication(Map<String, String> parameters) {
        var mobile = parameters.get(USERNAME_KEY);
        var validCode = parameters.get("validCode");
        try {
            getMobileVerificationCodeApplication().validCode(mobile,validCode);
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidGrantException(e.getMessage());
        }
    }


}
