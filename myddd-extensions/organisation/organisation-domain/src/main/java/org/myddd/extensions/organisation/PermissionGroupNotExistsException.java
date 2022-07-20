package org.myddd.extensions.organisation;

import org.myddd.lang.BusinessException;

public class PermissionGroupNotExistsException extends BusinessException {

    public PermissionGroupNotExistsException(Long permissionGroupId) {
        super(OrganizationErrorCode.PERMISSION_GROUP_NOT_EXISTS,String.valueOf(permissionGroupId));
    }
}
