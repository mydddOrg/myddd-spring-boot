package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class EmailOrPhoneMustHaveOneException extends BusinessException {
    public EmailOrPhoneMustHaveOneException() {
        super(UserErrorCode.EMAIL_OR_PHONE_MUST_HAVE_ONE);
    }
}
