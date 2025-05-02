package com.third.games.users.controller;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.result.Result;
import com.third.games.common.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@NoAuth
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService codeService;

    @PostMapping("/verify/code")
    Result<String> getVerifyCode(HttpServletRequest request) {
        String ip = String.valueOf(request.getAttribute("realIp"));
        return codeService.send(0, "账号注册", "你的验证码是【123456】,请不要将你的验证码分享给别人", "86", "13111111111", ip, VerifyCodeTypeEnum.fromCode(1));
    }
}
