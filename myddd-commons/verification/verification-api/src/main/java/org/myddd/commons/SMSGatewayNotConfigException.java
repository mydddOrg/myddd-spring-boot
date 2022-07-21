package org.myddd.commons;

import org.myddd.lang.BusinessException;

public class SMSGatewayNotConfigException extends BusinessException {

    public SMSGatewayNotConfigException() {
        super(VerificationErrorCode.SMS_GATEWAY_NO_CONFIG);
    }

}
