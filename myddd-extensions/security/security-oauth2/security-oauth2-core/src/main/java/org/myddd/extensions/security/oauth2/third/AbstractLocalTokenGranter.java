package org.myddd.extensions.security.oauth2.third;

import org.myddd.domain.InstanceFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractLocalTokenGranter extends AbstractThirdTokenGranter{


    private static UserDetailsService userDetailsService;

    public AbstractLocalTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    private static UserDetailsService getUserDetailsService(){
        if(Objects.isNull(userDetailsService)){
            userDetailsService = InstanceFactory.getInstance(UserDetailsService.class);
        }
        return userDetailsService;
    }

    abstract String getUsernameKey();

    @Override
    UserDetails queryUserDetails(Map<String, String> parameters) {
        var username = parameters.get(getUsernameKey());
        return getUserDetailsService().loadUserByUsername(username);
    }
}
