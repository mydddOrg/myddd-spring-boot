package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class UserNameEmptyException extends BusinessException {

    public UserNameEmptyException() {
        super(UserErrorCode.USER_NAME_EMPTY);
    }

}
