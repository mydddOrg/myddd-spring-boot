package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(OrganizationErrorCode.USER_NOT_FOUND,String.valueOf(userId));
    }
}
