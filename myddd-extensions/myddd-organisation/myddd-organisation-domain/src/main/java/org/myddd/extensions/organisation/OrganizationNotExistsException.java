package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OrganizationNotExistsException extends BusinessException {

    public OrganizationNotExistsException(long orgId) {
        super(OrganizationErrorCode.ORG_NOT_EXISTS,String.valueOf(orgId));
    }
}
