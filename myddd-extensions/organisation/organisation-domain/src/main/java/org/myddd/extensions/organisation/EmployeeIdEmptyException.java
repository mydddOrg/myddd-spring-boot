package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeIdEmptyException extends BusinessException {

    public EmployeeIdEmptyException() {
        super(EmployeeErrorCode.EMPLOYEE_ID_EMPTY);
    }

}
