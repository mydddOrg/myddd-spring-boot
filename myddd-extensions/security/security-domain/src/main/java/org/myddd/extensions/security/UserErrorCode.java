package org.myddd.extensions.security;

import org.myddd.lang.ErrorCode;

public enum UserErrorCode implements ErrorCode {

    /**
     * 用户ID为空
     */
    USER_ID_EMPTY,

    /**
     * 用户名为空
     */
    USER_NAME_EMPTY,

    /**
     * 密码为空
     */
    PASSWORD_EMPTY,

    /**
     * 用户未找到
     */
    USER_NOT_FOUND,

    /**
     * 不正确的邮箱格式
     */
    ILLEGAL_EMAIL_FORMAT,

    /**
     * 不正确的手机号格式
     */
    ILLEGAL_PHONE_FORMAT,

    /**
     * 邮箱手机号必须有一个不为空
     */
    EMAIL_OR_PHONE_MUST_HAVE_ONE,

    /**
     * 邮箱已存在
     */
    EMAIL_EXISTS,

    /**
     * 手机号已存在
     */
    PHONE_EXISTS,
}
