package org.myddd.extensions.organisation;

import org.myddd.lang.ErrorCode;

public enum OrganizationErrorCode implements ErrorCode {

    /**
     * 组织不存在
     */
    ORG_NOT_EXISTS,

    /**
     * 用户不存在
     */
    USER_NOT_FOUND,

    /**
     * 用户不在当前组织中
     */
    USER_NOT_FOUND_IN_ORG,

    /**
     * 权限组不存在
     */
    PERMISSION_GROUP_NOT_EXISTS,

    /**
     * 只允许公司才能操作
     */
    ONLY_ALLOWED_COMPANY,

    /**
     * 不允许修改第三方同步数据
     */
    NOT_ALLOWED_MODIFY_THIRD_SOURCE,

    /**
     * 不允许删除顶级公司
     */
    TOP_ORGANIZATION_NOT_ALLOWED_DELETE,

    /**
     * 部门下雇员不为空
     */
    ORGANIZATION_EMPLOYEE_NOT_EMPTY,

    /**
     * 部门名称已存在
     */
    ORGANIZATION_NAME_EXISTS

}
