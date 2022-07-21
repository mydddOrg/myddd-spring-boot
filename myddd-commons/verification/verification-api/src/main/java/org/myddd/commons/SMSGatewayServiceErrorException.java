package org.myddd.commons;

import org.myddd.lang.BusinessException;

public class SMSGatewayServiceErrorException extends BusinessException {

    public SMSGatewayServiceErrorException() {
        super(VerificationErrorCode.SMS_GATEWAY_SERVICE_ERROR);
    }

}
