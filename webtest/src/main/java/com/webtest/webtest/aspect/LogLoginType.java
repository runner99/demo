package com.webtest.webtest.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogLoginType {

    LOGIN(1, "登入"),
    LOGOUT(2, "登出");

    private final Integer code;
    private final String message;

}
