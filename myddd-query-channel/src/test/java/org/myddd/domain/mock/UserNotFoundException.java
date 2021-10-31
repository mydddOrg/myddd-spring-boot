package org.myddd.domain.mock;

import org.myddd.lang.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String userId) {
        super(UserErrorCode.USER_NOT_FOUND,userId);
    }
}
