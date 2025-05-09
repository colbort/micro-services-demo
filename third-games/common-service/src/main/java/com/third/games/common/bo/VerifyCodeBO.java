package com.third.games.common.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeBO {
    private String verifyCode; // 验证码
    private String verifyId; // 验证码对应的值
}
