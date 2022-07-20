package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;

public class RoleIdEmptyException extends BusinessException {
    public RoleIdEmptyException() {
        super(RoleErrorCode.ROLE_ID_EMPTY);
    }
}

