package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class UserNotExistsException extends BusinessException {

    public UserNotExistsException() {
        super(UserRestError.USER_NOT_EXISTS);
    }
}
