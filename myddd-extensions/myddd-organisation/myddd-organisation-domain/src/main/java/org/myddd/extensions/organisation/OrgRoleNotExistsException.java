package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OrgRoleNotExistsException extends BusinessException {

    public OrgRoleNotExistsException(Long roleId) {
        super(OrgRoleErrorCode.ORG_ROLE_NOT_EXISTS,String.valueOf(roleId));
    }

}
