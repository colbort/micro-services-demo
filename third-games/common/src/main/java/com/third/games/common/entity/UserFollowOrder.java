package com.third.games.common.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户跟单订单表
 * </p>
 *
 * @author baomidou
 * @since 2025-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_follow_order")
public class UserFollowOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 姓名
     */
    @TableField("user_real_name")
    private String userRealName;

    /**
     * 手机号
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 跟单金额
     */
    @TableField("follow_amount")
    private BigDecimal followAmount;

    /**
     * 追加金额
     */
    @TableField("additional_amount")
    private BigDecimal additionalAmount;

    /**
     * 仓位
     */
    @TableField("position")
    private BigDecimal position;

    /**
     * 导师ID
     */
    @TableField("teacher_id")
    private Integer teacherId;

    /**
     * 姓名
     */
    @TableField("teacher_real_name")
    private String teacherRealName;

    /**
     * 手机号
     */
    @TableField("teacher_phone")
    private String teacherPhone;

    /**
     * 跟单金额最小值
     */
    @TableField("follow_amount_min")
    private BigDecimal followAmountMin;

    /**
     * 跟单金额最大值
     */
    @TableField("follow_amount_max")
    private BigDecimal followAmountMax;

    /**
     * 产品ID
     */
    @TableField("user_packages_id")
    private Integer userPackagesId;

    /**
     * 跟单类型；ORDINARY_FOLLOW("普通跟单"), PACKAGE_PRODUCT("套餐产品");
     */
    @TableField("follow_type")
    private String followType;

    /**
     * 跟单状态；PENDING("待审核"), FOLLOWING("跟单中"), REJECTED("被驳回"), CANCELLED("已撤销"), ENDED("已结束"), STOPPED("已中止");
     */
    @TableField("follow_status")
    private String followStatus;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private LocalDateTime applyTime;

    /**
     * 合约到期时间
     */
    @TableField("contract_expiration_time")
    private LocalDateTime contractExpirationTime;

    /**
     * 追加跟单ID
     */
    @TableField("append_order_id")
    private Integer appendOrderId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 上级ID
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;


}
