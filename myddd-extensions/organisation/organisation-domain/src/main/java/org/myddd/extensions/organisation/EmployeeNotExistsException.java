package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeNotExistsException extends BusinessException {

    public EmployeeNotExistsException(Long employeeId) {
        super(EmployeeErrorCode.EMPLOYEE_NOT_EXISTS,String.valueOf(employeeId));
    }
}
