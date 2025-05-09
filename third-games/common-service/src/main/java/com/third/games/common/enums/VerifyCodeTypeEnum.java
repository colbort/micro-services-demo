package com.third.games.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerifyCodeTypeEnum {
    PHONE(1, "phone"),
    EMAIL(2, "email");

    @EnumValue
    private final int code;
    private final String label;

    public static VerifyCodeTypeEnum fromCode(Integer code) {
        if (code == null) return null;
        for (VerifyCodeTypeEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
