package org.myddd.commons;

import org.myddd.lang.ErrorCode;

public enum VerificationErrorCode implements ErrorCode {

    /**
     * 短信网关未配置
     */
    SMS_GATEWAY_NO_CONFIG,

    /**
     * 短信网关服务异常
     */
    SMS_GATEWAY_SERVICE_ERROR,
}
