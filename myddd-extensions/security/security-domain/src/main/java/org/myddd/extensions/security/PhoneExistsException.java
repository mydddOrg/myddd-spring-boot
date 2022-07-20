package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class PhoneExistsException extends BusinessException {
    public PhoneExistsException(String phone) {
        super(UserErrorCode.PHONE_EXISTS,phone);
    }
}
