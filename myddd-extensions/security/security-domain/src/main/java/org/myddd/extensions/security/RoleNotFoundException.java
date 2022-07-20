package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class RoleNotFoundException extends BusinessException {

    public RoleNotFoundException() {
        super(RoleErrorCode.ROLE_NOT_FOUND);
    }
}
