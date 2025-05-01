package com.third.games.common.vo;


import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

@Data
public class UserPrivateVO {
    private Long id; // 主键ID
    private Long userId; // 关联用户ID（t_user.id）
    private String realName; // 真实姓名
    private String idCardNo; // 证件号码
    private Integer idCardType; // 证件类型：1-身份证，2-护照等
    private String idCardFrontUrl; // 证件正面照片URL
    private String idCardBackUrl; // 证件背面照片URL
    private String idCardHandheldUrl; // 手持证件照片URL
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verifiedTime; // 实名认证时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间
    private Integer isDeleted; // 逻辑删除：0-正常，1-删除
}
