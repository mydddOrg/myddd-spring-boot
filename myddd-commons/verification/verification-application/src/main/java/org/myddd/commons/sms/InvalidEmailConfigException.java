package org.myddd.commons.sms;

import org.myddd.lang.BusinessException;

public class InvalidEmailConfigException extends BusinessException {
    public InvalidEmailConfigException() {
        super(VerificationErrorCode.INVALID_EMAIL_CONFIG);
    }
}
