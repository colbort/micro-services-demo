package com.third.games.common.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerifyCodeLogDTO {
    private Long id; // 主键ID
    private String account; // 账号（手机号或邮箱）
    private String title; // 验证码用途标题（如：注册、登录、找回密码）
    private String content; // 发送内容（一般为验证码内容）
    private Byte sendType; // 发送类型：0-未知，1-短信，2-邮件
    private String ip; // 发送请求来源 IP
    private Byte status; // 发送状态：1-成功，0-失败
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
}
