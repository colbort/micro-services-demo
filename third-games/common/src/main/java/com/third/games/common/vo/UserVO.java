package com.third.games.common.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id; // 主键ID
    private String username; // 用户名
    private String nickname; // 用户昵称
    private String password; // 加密后的密码
    private String phone; // 手机号
    private String email; // 邮箱
    private String avatar; // 头像地址
    private Integer gender; // 性别：0-未知，1-男，2-女
    private Integer status; // 状态：1-启用，0-禁用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间
    private Integer isDeleted; // 逻辑删除：0-正常，1-已删除
    private String registerIp; // 注册IP地址
    private String registerChannel; // 注册渠道，如 wechat、ios、h5
    private String deviceType; // 注册设备类型：ios、android、pc等
    private String deviceId; // 设备唯一标识
    private Long parentId; // 上级用户ID（推荐人）
}
