package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OnlyAllowedCompanyException extends BusinessException {

    public OnlyAllowedCompanyException() {
        super(OrganizationErrorCode.ONLY_ALLOWED_COMPANY);
    }
}
