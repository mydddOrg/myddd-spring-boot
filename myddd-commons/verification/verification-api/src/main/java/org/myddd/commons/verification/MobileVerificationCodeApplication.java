package org.myddd.commons.verification;

public interface MobileVerificationCodeApplication {
    /**
     * 向指定手机发送一条验证码
     * @param mobile 手机号
     */
    void sendCode(String mobile);

    /**
     * 检查手机+验证码的正确性
     * @param mobile 手机号
     * @param code 验证码
     */
    void validCode(String mobile,String code);
}
