package org.myddd.extensions.security;

import org.myddd.lang.BusinessException;


public class RoleNameEmptyException extends BusinessException {
    public RoleNameEmptyException() {
        super(RoleErrorCode.ROLE_NAME_EMPTY);
    }
}
