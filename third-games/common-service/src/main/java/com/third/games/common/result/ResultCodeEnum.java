package com.third.games.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "服务器异常"),

    // 通用错误
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "资源不存在"),

    // 用户模块
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_EXISTS(1002, "用户已存在"),
    INVALID_PHONE(1003, "手机号不合法"),
    PASSWORD_ERROR(1004, "密码错误"),

    DELETE_ERROR(2001, "销毁数据失败");

    private final int code;
    private final String message;
}
