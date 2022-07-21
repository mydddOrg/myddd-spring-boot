package org.myddd.extensions.security;

/**
 * 授权的相关接口，供REST模块调用
 */
public interface IAuthentication {

    /**
     * 当前用户是否认证
     * @return
     */
    boolean isAuthentication();


    Long loginUserId();
}
