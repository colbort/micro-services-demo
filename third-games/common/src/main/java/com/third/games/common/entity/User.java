package com.third.games.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.third.games.common.enums.DeviceTypeEnum;
import com.third.games.common.enums.GenderEnum;
import com.third.games.common.enums.UserStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.EnumTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author baomidou
 * @since 2025-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 加密后的密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别：0-未知，1-男，2-女
     */
    @TableField(value = "gender", typeHandler = EnumTypeHandler.class)
    private GenderEnum gender;

    /**
     * 状态：1-启用，0-禁用
     */
    @TableField(value = "status", typeHandler = EnumTypeHandler.class)
    private UserStatusEnum status;

    /**
     * 注册IP地址
     */
    @TableField("register_ip")
    private String registerIp;

    /**
     * 注册渠道，如 wechat、ios、h5
     */
    @TableField("register_channel")
    private String registerChannel;

    /**
     * 注册设备类型：ios、android、pc等
     */
    @TableField(value = "device_type", typeHandler = EnumTypeHandler.class)
    private DeviceTypeEnum deviceType;

    /**
     * 设备唯一标识
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 上级用户ID（推荐人）
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-正常，1-已删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

}
