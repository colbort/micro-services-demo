package com.third.games.common.vo;


import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

@Data
public class UserAddressVO {
    private Long id; // 主键ID
    private Long userId; // 关联用户ID（t_user.id）
    private String receiverName; // 收件人姓名
    private String receiverPhone; // 收件人手机号
    private String province; // 省
    private String city; // 市
    private String district; // 区/县
    private String detailAddress; // 详细地址
    private Integer isDefault; // 是否默认地址：0-否，1-是
    private String postalCode; // 邮编（可选）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间
    private Integer isDeleted; // 逻辑删除：0-正常，1-删除
}
