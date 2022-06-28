package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class IllegalEmailFormatException extends BusinessException {

    public IllegalEmailFormatException(String email) {
        super(UserErrorCode.ILLEGAL_EMAIL_FORMAT,email);
    }

}
