package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class PhoneEmailCanNotAllEmptyException extends BusinessException {

    public PhoneEmailCanNotAllEmptyException() {
        super(EmployeeErrorCode.PHONE_EMAIL_CAN_NOT_ALL_EMPTY);
    }
}
