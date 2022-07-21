package org.myddd.commons.verification;

public interface EmailVerificationCodeApplication {

    /**
     * 向指定的邮箱发送一条验证码
     * @param email 指定的邮箱
     */
    void sendEmailCode(String email);

    /**
     * 检查邮箱+验证码的正确性
     * @param email 邮箱
     * @param code 验证码
     */
    void validEmailCode(String email, String code);

}
