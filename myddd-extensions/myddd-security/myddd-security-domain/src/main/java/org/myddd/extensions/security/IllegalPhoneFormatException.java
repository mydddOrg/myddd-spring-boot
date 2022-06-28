package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class IllegalPhoneFormatException extends BusinessException {

    public IllegalPhoneFormatException(String phone) {
        super(UserErrorCode.ILLEGAL_PHONE_FORMAT,phone);
    }

}
