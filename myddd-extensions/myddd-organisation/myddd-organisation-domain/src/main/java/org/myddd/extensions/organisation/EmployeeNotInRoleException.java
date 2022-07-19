package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeNotInRoleException extends BusinessException {

    public EmployeeNotInRoleException() {
        super(EmployeeErrorCode.EMPLOYEE_NOT_IN_ROLE);
    }
}
