package com.third.games.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IdCardTypeEnum {
    ID_CARD(1, "身份证"),
    PASSPORT(2, "护照"),
    OTHER(99, "其他");

    private final int code;
    private final String label;

    public static IdCardTypeEnum fromCode(Integer code) {
        if (code == null) return null;
        for (IdCardTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return OTHER;
    }
}
