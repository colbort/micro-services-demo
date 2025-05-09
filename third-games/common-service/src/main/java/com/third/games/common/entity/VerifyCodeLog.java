package com.third.games.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 验证码发送记录表
 * </p>
 *
 * @author baomidou
 * @since 2025-05-07
 */
@Getter
@Setter
@TableName("t_verify_code_log")
public class VerifyCodeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号（手机号或邮箱）
     */
    @TableField("account")
    private String account;

    /**
     * 验证码用途标题（如：注册、登录、找回密码）
     */
    @TableField("title")
    private String title;

    /**
     * 发送内容（一般为验证码内容）
     */
    @TableField("content")
    private String content;

    /**
     * 发送类型：0-未知，1-短信，2-邮件
     */
    @TableField("send_type")
    private VerifyCodeTypeEnum sendType;

    /**
     * 发送请求来源 IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 发送状态：1-成功，0-失败
     */
    @TableField("status")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
