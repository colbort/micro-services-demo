package com.third.games.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author baomidou
 * @since 2025-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_address")
public class UserAddress implements Serializable {

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
     * 收件人姓名
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 收件人手机号
     */
    @TableField("receiver_phone")
    private String receiverPhone;

    /**
     * 省
     */
    @TableField("province")
    private String province;

    /**
     * 市
     */
    @TableField("city")
    private String city;

    /**
     * 区/县
     */
    @TableField("district")
    private String district;

    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;

    /**
     * 是否默认地址：0-否，1-是
     */
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 邮编（可选）
     */
    @TableField("postal_code")
    private String postalCode;

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
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;


}
