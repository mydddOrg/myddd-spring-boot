package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class OrgRoleGroupNotExistsException extends BusinessException {

    public OrgRoleGroupNotExistsException(Long id) {
        super(OrgRoleErrorCode.ORG_ROLE_GROUP_NOT_EXISTS,String.valueOf(id));
    }

}
