package org.myddd.extensions.organisation;

import org.myddd.lang.ErrorCode;

public enum EmployeeErrorCode implements ErrorCode {
    /**
     * 雇员不在公司中
     */
    EMPLOYEE_NOT_IN_COMPANY,
    /**
     * 雇员不存在
     */
    EMPLOYEE_NOT_EXISTS,
    /**
     * 雇员不在角色中
     */
    EMPLOYEE_NOT_IN_ROLE,

    /**
     * 雇员编码已存在
     */
    EMPLOYEE_ID_EXISTS,

    /**
     * 手机与邮箱不能同时为空
     */
    PHONE_EMAIL_CAN_NOT_ALL_EMPTY,

    /**
     * 员工编号为空
     */
    EMPLOYEE_ID_EMPTY,

    /**
     * 此用户已在指定组织中
     */
    EMPLOYEE_USER_ID_EXISTS,

    /**
     * 当前组织已存在此邮件的雇员
     */
    EMPLOYEE_EMAIL_EXISTS,

    /**
     * 当前组织已存在此手机号的雇员
     */
    EMPLOYEE_PHONE_EXISTS,

}