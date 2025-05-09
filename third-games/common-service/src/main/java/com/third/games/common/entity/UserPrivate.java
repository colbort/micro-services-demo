package com.third.games.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.third.games.common.enums.IdCardTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户实名信息表
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@Getter
@Setter
@TableName("t_user_private")
public class UserPrivate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联用户ID（t_user.id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 证件号码
     */
    @TableField("id_card_no")
    private String idCardNo;

    /**
     * 证件类型：1-身份证，2-护照等
     */
    @TableField("id_card_type")
    private IdCardTypeEnum idCardType;

    /**
     * 证件正面照片URL
     */
    @TableField("id_card_front_url")
    private String idCardFrontUrl;

    /**
     * 证件背面照片URL
     */
    @TableField("id_card_back_url")
    private String idCardBackUrl;

    /**
     * 手持证件照片URL
     */
    @TableField("id_card_handheld_url")
    private String idCardHandheldUrl;

    /**
     * 实名认证时间
     */
    @TableField("verified_time")
    private LocalDateTime verifiedTime;

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
     * 逻辑删除：0-正常，1-删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
