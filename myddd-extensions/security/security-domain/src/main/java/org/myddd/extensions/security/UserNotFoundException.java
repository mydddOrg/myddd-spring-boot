package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long id) {
        super(UserErrorCode.USER_NOT_FOUND,String.valueOf(id));
    }
}
