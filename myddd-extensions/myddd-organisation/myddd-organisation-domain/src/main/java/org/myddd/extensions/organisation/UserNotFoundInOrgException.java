package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class UserNotFoundInOrgException extends BusinessException {

    public UserNotFoundInOrgException(Long userId, long orgId) {
        super(OrganizationErrorCode.USER_NOT_FOUND_IN_ORG,String.valueOf(userId),String.valueOf(orgId));
    }
}
