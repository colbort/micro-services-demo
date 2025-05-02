package com.third.games.users.controller;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.bo.UserBO;
import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.UserContext;
import com.third.games.common.vo.UserVO;
import com.third.games.users.feign.ProductServiceClient;
import com.third.games.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    @NoAuth
    public Result<UserVO> register(@RequestBody UserBO request) {
        return userService.register(request);
    }

    @PostMapping("/user/login")
    @NoAuth
    public Result<UserVO> login(@RequestBody UserBO request) {
        return userService.login(request);
    }

    @GetMapping("/user/logout")
    public Result<Boolean> logout() {
        LoginUser loginUser = UserContext.get();
        return userService.logout(loginUser);
    }
}
