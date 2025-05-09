package com.third.games.user.controller;

import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.UserContext;
import com.third.games.common.vo.UserPrivateVO;
import com.third.games.user.service.IUserPrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户实名信息表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserPrivateController {
    @Autowired
    private IUserPrivateService privateService;
    @Autowired
    private UserContext userContext;

    @GetMapping("/info")
    public Result<UserPrivateVO> info() {
        LoginUser loginUser = userContext.get();
        return privateService.info(loginUser);
    }
}
