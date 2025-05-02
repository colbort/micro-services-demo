package com.third.games.users.controller;

import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.UserContext;
import com.third.games.common.vo.UserPrivateVO;
import com.third.games.users.service.IUserPrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserPrivateController {
    @Autowired
    private IUserPrivateService privateService;

    @GetMapping("/user/info")
    public Result<UserPrivateVO> info() {
        LoginUser loginUser = UserContext.get();
        return privateService.info(loginUser);
    }
}
