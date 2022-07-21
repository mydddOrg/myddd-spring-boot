package org.myddd.extensions.security.oauth2.third;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractThirdTokenGranter extends AbstractTokenGranter {



    public AbstractThirdTokenGranter(AuthorizationServerTokenServices tokenServices,
                                    ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        authentication(parameters);

        var user = queryUserDetails(parameters);
        var userAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    abstract UserDetails queryUserDetails(Map<String, String> parameters);

    abstract void authentication(Map<String, String> parameters);
}
