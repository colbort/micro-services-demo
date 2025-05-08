package com.third.games.captcha.controller;

import com.third.games.captcha.service.IVerifyCodeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/verifyCodeLog")
public class VerifyCodeLogController {
    @Autowired
    private IVerifyCodeLogService verifyCodeLogService;

    @GetMapping("test")
    public Long test() {
        return verifyCodeLogService.count();
    }
}
