package com.third.games.captcha.controller;

import com.third.games.captcha.service.IVerifyCodeService;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 验证码发送记录表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-05-07
 */
@RestController
@RequestMapping("/api/v1/verify")
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService codeService;

    @PostMapping("get")
    Result<String> getVerifyCode(HttpServletRequest request) {
        String ip = String.valueOf(request.getAttribute("realIp"));
        return codeService.send(0, "账号注册", "你的验证码是【123456】,请不要将你的验证码分享给别人", "86", "13111111111", ip, VerifyCodeTypeEnum.fromCode(1));
    }

    @GetMapping("test")
    public Long test() {
        return codeService.count();
    }
}
