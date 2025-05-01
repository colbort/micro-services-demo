package com.third.games.common.exception;

import com.third.games.common.result.ResultCodeEnum;

public class BizException extends RuntimeException {
    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}

