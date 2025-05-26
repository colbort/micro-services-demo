package com.third.games.common.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户注册/登录请求体")
public class UserBO {
    @Schema(description = "用户名", example = "tom")
    private String username; // 用户名
    @Schema(description = "昵称", example = "tom")
    private String nickname; // 用户昵称
    @Schema(description = "密码", example = "123456")
    private String password; // 加密后的密码
    @Schema(description = "手机号", example = "13111111111")
    private String phone; // 手机号
    @Schema(description = "邮箱", example = "tom@gmail.com")
    private String email; // 邮箱
    @Schema(description = "头像", example = "tom")
    private String avatar; // 头像地址
    @Schema(description = "性别", example = "tom")
    private Integer gender; // 性别：0-未知，1-男，2-女
    private String registerIp; // 注册IP地址
    @Schema(description = "注册渠道", example = "h5")
    private String registerChannel; // 注册渠道，如 wechat、ios、h5
    @Schema(description = "注册设备类型", example = "pc")
    private String deviceType; // 注册设备类型：ios、android、pc等
    @Schema(description = "设备唯一标识", example = "123456789")
    private String deviceId; // 设备唯一标识
    @Schema(description = "上级ID", example = "001")
    private Long parentId; // 上级用户ID（推荐人）
    @Schema(description = "是否验证码注册/登录", example = "false")
    private Boolean codeLogin; // 是否验证码注册/登录
    @Schema(description = "验证码", example = "123456")
    private String verifyCode; // 验证码内容
    @Schema(description = "验证码对应的ID", example = "11111")
    private String verifyId; // 验证码对应的ID
}
