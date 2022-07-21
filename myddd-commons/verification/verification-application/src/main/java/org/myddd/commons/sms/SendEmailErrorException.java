package org.myddd.commons.sms;

import org.myddd.lang.BusinessException;

public class SendEmailErrorException extends BusinessException {
    public SendEmailErrorException(String msg) {
        super(VerificationErrorCode.SEND_EMAIL_ERROR,msg);
    }
}
