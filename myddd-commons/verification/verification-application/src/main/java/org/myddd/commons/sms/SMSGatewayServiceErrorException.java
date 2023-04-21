package org.myddd.commons.sms;

import org.myddd.lang.BusinessException;

public class SMSGatewayServiceErrorException extends BusinessException {
    public SMSGatewayServiceErrorException() {
        super(VerificationErrorCode.SMS_SERVICE_ERROR);
    }
}
