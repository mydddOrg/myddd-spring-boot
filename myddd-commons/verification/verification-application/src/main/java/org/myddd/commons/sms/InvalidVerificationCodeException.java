package org.myddd.commons.sms;

import org.myddd.lang.BusinessException;

public class InvalidVerificationCodeException extends BusinessException {

    public InvalidVerificationCodeException() {
        super(VerificationErrorCode.INVALID_VERIFICATION_CODE);
    }

}
