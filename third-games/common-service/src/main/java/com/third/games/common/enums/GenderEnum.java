package com.third.games.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final int code;
    private final String label;

    public static GenderEnum fromCode(Integer code) {
        for (GenderEnum e : values()) {
            if (e.getCode() == code) return e;
        }
        return UNKNOWN;
    }
}

