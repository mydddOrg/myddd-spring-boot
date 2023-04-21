package org.myddd.commons.sms;

import org.myddd.lang.BusinessException;

public class SMSConfigErrorException extends BusinessException {
    public SMSConfigErrorException() {
        super(VerificationErrorCode.SMS_CONFIG_ERROR);
    }
}
