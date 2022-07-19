package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeIdExistsException extends BusinessException {
    public EmployeeIdExistsException(String employeeId) {
        super(EmployeeErrorCode.EMPLOYEE_ID_EXISTS,employeeId);
    }
}
