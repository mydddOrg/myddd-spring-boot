package org.myddd.extensions.organisation;

import org.myddd.lang.ErrorCode;

public enum OrgRoleErrorCode implements ErrorCode {
    /**
     * 组织角色不存在
     */
    ORG_ROLE_NOT_EXISTS,

    /**
     * 角色不为空
     */
    ORG_ROLE_NOT_EMPTY,

    /**
     * 组织角色组不存在
     */
    ORG_ROLE_GROUP_NOT_EXISTS,

    /**
     * 组织角色组不为空
     */
    ORG_ROLE_GROUP_NOT_EMPTY,
}
