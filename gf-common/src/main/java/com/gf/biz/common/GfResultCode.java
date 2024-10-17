package com.gf.biz.common;

import java.io.Serializable;

public enum GfResultCode implements IGfResultCode, Serializable {

    SUCCESS("00000",null),

    USER_ERROR("A0001","用户端错误"),

    USER_LOGIN_ERROR("A0200","用户登录异常"),

    USER_NOT_EXIST("A0201","用户不存在"),

    USER_ACCOUNT_LOCKED("A0202","用户账户被冻结"),

    USER_ACCOUNT_INVALID("A0203","用户账户已作废"),

    USERNAME_OR_PASSWORD_ERROR("A0210","用户名或密码错误"),

    PASSWORD_ENTER_EXCEED_LIMIT("A0211","用户输入密码次数超限"),

    CLIENT_AUTHENTICATION_FAILED("A0212","客户端认证失败"),

    TOKEN_INVALID_OR_EXPIRED("A0230","token无效或已过期"),

    TOKEN_ACCESS_FORBIDDEN("A0231","token已被禁止访问"),

    AUTHORIZED_ERROR("A0300","访问权限异常"),

    ACCESS_UNAUTHORIZED("A0301","访问未授权"),

    FORBIDDEN_OPERATION("A0302","演示环境禁止修改、删除重要数据，请本地部署后测试"),

    SYSTEM_EXECUTION_ERROR("B0001","系统执行出错"),

    SYSTEM_EXECUTION_TIMEOUT("B0100","系统执行超时"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200","系统容灾功能被触发"),

    BUSINESS_COMMON_EXCEPTION("BIZ0001","业务通用异常"),

    FLOW_LIMITING("B0210","系统限流"),

    DEGRADATION("B0220","系统功能降级");

    GfResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;

    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
