package com.third.games.common.bo;


import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

@Data
public class UserPrivateBO {
    private String realName; // 真实姓名
    private String idCardNo; // 证件号码
    private Integer idCardType; // 证件类型：1-身份证，2-护照等
    private String idCardFrontUrl; // 证件正面照片URL
    private String idCardBackUrl; // 证件背面照片URL
    private String idCardHandheldUrl; // 手持证件照片URL
}
