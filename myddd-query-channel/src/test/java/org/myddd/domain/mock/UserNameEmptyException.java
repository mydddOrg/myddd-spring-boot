package org.myddd.domain.mock;

import org.myddd.lang.BusinessException;

public class UserNameEmptyException extends BusinessException {

    public UserNameEmptyException() {
        super(UserErrorCode.USER_NAME_EMPTY);
    }

}
