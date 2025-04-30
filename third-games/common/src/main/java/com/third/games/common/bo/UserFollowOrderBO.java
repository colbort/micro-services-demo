package com.third.games.common.bo;


import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.lang.Integer;
import java.lang.String;

@Data
public class UserFollowOrderBO {
    private Integer id; // 唯一标识符
    private String orderNo; // 单号
    private Integer userId; // 用户ID
    private String userRealName; // 姓名
    private String userPhone; // 手机号
    private BigDecimal followAmount; // 跟单金额
    private BigDecimal additionalAmount; // 追加金额
    private BigDecimal position; // 仓位
    private Integer teacherId; // 导师ID
    private String teacherRealName; // 姓名
    private String teacherPhone; // 手机号
    private BigDecimal followAmountMin; // 跟单金额最小值
    private BigDecimal followAmountMax; // 跟单金额最大值
    private Integer userPackagesId; // 产品ID
    private String followType; // 跟单类型；ORDINARY_FOLLOW("普通跟单"), PACKAGE_PRODUCT("套餐产品");
    private String followStatus; // 跟单状态；PENDING("待审核"), FOLLOWING("跟单中"), REJECTED("被驳回"), CANCELLED("已撤销"), ENDED("已结束"), STOPPED("已中止");
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime; // 申请时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contractExpirationTime; // 合约到期时间
    private Integer appendOrderId; // 追加跟单ID
    private String remark; // 备注
    private Integer parentId; // 上级ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // 更新时间
}
