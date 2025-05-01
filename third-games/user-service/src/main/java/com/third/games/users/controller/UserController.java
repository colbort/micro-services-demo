package com.third.games.users.controller;

import com.third.games.common.bo.UserBO;
import com.third.games.common.result.Result;
import com.third.games.common.vo.UserPrivateVO;
import com.third.games.common.vo.UserVO;
import com.third.games.users.feign.ProductServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private ProductServiceClient productServiceClient;

    @PostMapping("/user/register")
    public Result<UserVO> register(@RequestBody UserBO request) {
        return null;
    }

    @PostMapping("/user/login")
    public Result<UserVO> login(@RequestBody UserBO request) {
        return null;
    }

    @GetMapping("/user/info")
    public Result<UserPrivateVO> info(@RequestBody UserBO request) {
        return null;
    }
}
