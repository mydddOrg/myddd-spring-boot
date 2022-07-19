package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class EmployeeNotInCompanyException extends BusinessException {

    public EmployeeNotInCompanyException() {
        super(EmployeeErrorCode.EMPLOYEE_NOT_IN_COMPANY);
    }
}
