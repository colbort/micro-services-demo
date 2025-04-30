package com.third.games.common.entity;

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
 * 会员邀请/被邀请记录表
 * </p>
 *
 * @author baomidou
 * @since 2025-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_invitation")
public class UserInvitation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邀请人（会员）ID
     */
    @TableField("inviter_id")
    private Integer inviterId;

    /**
     * 邀请人类型
     */
    @TableField("parent_type")
    private String parentType;

    /**
     * 被邀请人（会员）ID
     */
    @TableField("invitee_id")
    private Integer inviteeId;

    /**
     * 邀请时间
     */
    @TableField("invited_at")
    private LocalDateTime invitedAt;


}
