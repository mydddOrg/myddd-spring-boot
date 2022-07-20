package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OrgRoleGroupNotEmptyException extends BusinessException {
    public OrgRoleGroupNotEmptyException(Long id) {
        super(OrgRoleErrorCode.ORG_ROLE_GROUP_NOT_EMPTY,String.valueOf(id));
    }
}
