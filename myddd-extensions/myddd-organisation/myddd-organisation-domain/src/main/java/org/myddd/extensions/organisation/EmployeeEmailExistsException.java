package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeEmailExistsException extends BusinessException {
    public EmployeeEmailExistsException(String email) {
        super(EmployeeErrorCode.EMPLOYEE_EMAIL_EXISTS,email);
    }
}
