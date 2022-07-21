package org.myddd.commons.sms;

import org.myddd.lang.ErrorCode;

public enum VerificationErrorCode implements ErrorCode {

    /**
     * 验证码错误
     */
    INVALID_VERIFICATION_CODE,

    /**
     * 不正确的邮箱配置
     */
    INVALID_EMAIL_CONFIG,

    /**
     * 邮件发送错误
     */
    SEND_EMAIL_ERROR
}
