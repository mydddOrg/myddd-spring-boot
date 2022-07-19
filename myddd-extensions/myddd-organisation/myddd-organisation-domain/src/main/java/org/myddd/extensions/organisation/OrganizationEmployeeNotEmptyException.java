package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OrganizationEmployeeNotEmptyException extends BusinessException {

    public OrganizationEmployeeNotEmptyException(Long id) {
        super(OrganizationErrorCode.ORGANIZATION_EMPLOYEE_NOT_EMPTY,String.valueOf(id));
    }
}
