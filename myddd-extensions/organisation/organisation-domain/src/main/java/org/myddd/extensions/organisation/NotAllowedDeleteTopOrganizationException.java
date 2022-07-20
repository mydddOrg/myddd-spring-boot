package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class NotAllowedDeleteTopOrganizationException extends BusinessException {

    public NotAllowedDeleteTopOrganizationException(Long id) {
        super(OrganizationErrorCode.TOP_ORGANIZATION_NOT_ALLOWED_DELETE,String.valueOf(id));
    }
}
