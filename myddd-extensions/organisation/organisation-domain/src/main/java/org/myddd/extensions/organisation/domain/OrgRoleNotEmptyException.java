package org.myddd.extensions.organisation.domain;

import org.myddd.extensions.organisation.OrgRoleErrorCode;
import org.myddd.lang.BusinessException;

public class OrgRoleNotEmptyException extends BusinessException {

    public OrgRoleNotEmptyException() {
        super(OrgRoleErrorCode.ORG_ROLE_NOT_EMPTY);
    }

}
