package com.third.games.common.bo;


import lombok.Data;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

@Data
public class UserBO {
    private String username; // 用户名
    private String nickname; // 用户昵称
    private String password; // 加密后的密码
    private String phone; // 手机号
    private String email; // 邮箱
    private String avatar; // 头像地址
    private Integer gender; // 性别：0-未知，1-男，2-女
    private String registerIp; // 注册IP地址
    private String registerChannel; // 注册渠道，如 wechat、ios、h5
    private String deviceType; // 注册设备类型：ios、android、pc等
    private String deviceId; // 设备唯一标识
    private Long parentId; // 上级用户ID（推荐人）
}
