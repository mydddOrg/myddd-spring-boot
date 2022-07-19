package org.myddd.extensions.organisation.domain;

public interface EmployeeIdGenerator {
    /**
     * 生成一个随机的雇员编号
     * @return 一个随机的字符
     */
    String randomEmployeeId(int length);

    /**
     * 生成一个随机的雇员编号
     * @return 一个随机的字符
     */
    String randomEmployeeId();

    /**
     * 生成一个随机的雇员编号
     * @param prefix 前缀
     * @return 一个随机的字符
     */
    String randomEmployeeId(String prefix);
}
