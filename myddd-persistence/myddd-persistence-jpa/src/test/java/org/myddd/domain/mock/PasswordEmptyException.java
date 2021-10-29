package org.myddd.domain.mock;

import org.myddd.lang.BusinessException;

public class PasswordEmptyException extends BusinessException {

    public PasswordEmptyException() {
        super(UserErrorCode.PASSWORD_EMPTY);
    }
}
