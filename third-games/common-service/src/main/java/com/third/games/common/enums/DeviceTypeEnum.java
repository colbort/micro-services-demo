package com.third.games.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceTypeEnum {
    UNKNOWN("unknown", "未知设备"),
    ANDROID("android", "Android"),
    IOS("ios", "iOS"),
    H5("h5", "H5/浏览器"),
    PC("pc", "PC");

    @EnumValue
    private final String code;
    private final String label;

    public static DeviceTypeEnum fromCode(String code) {
        if (code == null) return UNKNOWN;
        for (DeviceTypeEnum type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
