package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class EmailExistsException extends BusinessException {
    public EmailExistsException(String email) {
        super(UserErrorCode.EMAIL_EXISTS,email);
    }
}
