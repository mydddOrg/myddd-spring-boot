package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeePhoneExistsException extends BusinessException {
    public EmployeePhoneExistsException(String phone) {
        super(EmployeeErrorCode.EMPLOYEE_PHONE_EXISTS,phone);
    }
}
