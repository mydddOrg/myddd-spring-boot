package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

/**
 * 部门名称已存在
 */
public class OrganizationNameExistsException extends BusinessException {
    public OrganizationNameExistsException(String name) {
        super(OrganizationErrorCode.ORGANIZATION_NAME_EXISTS,name);
    }
}
