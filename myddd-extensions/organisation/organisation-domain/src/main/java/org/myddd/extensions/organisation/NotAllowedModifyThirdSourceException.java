package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class NotAllowedModifyThirdSourceException extends BusinessException {

    public NotAllowedModifyThirdSourceException() {
        super(OrganizationErrorCode.NOT_ALLOWED_MODIFY_THIRD_SOURCE);
    }
}
