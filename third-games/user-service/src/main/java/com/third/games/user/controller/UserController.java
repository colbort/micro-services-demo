package com.third.games.user.controller;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.bo.UserBO;
import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.UserContext;
import com.third.games.common.vo.UserVO;
import com.third.games.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "用户接口", description = "用户信息相关接口")
public class UserController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private IUserService userService;

    @NoAuth
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册用户")
    public Result<UserVO> register(@RequestBody UserBO request) {
        return userService.register(request);
    }

    @NoAuth
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserBO request) {
        return userService.login(request);
    }

    @DeleteMapping("/logout")
    public Result<Boolean> logout() {
        LoginUser loginUser = userContext.get();
        return userService.logout(loginUser);
    }
}
