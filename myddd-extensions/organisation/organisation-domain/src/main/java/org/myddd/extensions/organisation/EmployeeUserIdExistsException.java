package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeUserIdExistsException extends BusinessException {

    public EmployeeUserIdExistsException(Long userId) {
        super(EmployeeErrorCode.EMPLOYEE_USER_ID_EXISTS,String.valueOf(userId));
    }
}
