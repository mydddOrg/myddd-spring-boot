package org.myddd.domain.mock;

import org.myddd.lang.BusinessException;

public class UserIdEmptyException extends BusinessException {

    public UserIdEmptyException() {
        super(UserErrorCode.USER_ID_EMPTY);
    }
}
