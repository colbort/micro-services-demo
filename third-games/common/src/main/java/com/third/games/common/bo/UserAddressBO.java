package com.third.games.common.bo;


import lombok.Data;
import java.lang.Integer;
import java.lang.String;

@Data
public class UserAddressBO {
    private String receiverName; // 收件人姓名
    private String receiverPhone; // 收件人手机号
    private String province; // 省
    private String city; // 市
    private String district; // 区/县
    private String detailAddress; // 详细地址
    private Integer isDefault; // 是否默认地址：0-否，1-是
    private String postalCode; // 邮编（可选）
}
