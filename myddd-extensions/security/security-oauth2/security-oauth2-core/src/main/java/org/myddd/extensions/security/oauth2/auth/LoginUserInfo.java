package org.myddd.extensions.security.oauth2.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoginUserInfo extends UserDetails {

    Long userId();

}
