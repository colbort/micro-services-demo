package com.third.games.common.service;

import com.third.games.common.security.LoginUser;

public interface TokenService {

    /**
     * 创建 token（登录成功时调用）
     */
    String createToken(LoginUser loginUser, String deviceType);

    /**
     * 从 token 中解析用户信息（拦截器中调用）
     */
    LoginUser parseToken(String token);

    /**
     * 校验 token 是否过期、合法
     */
    boolean validateToken(String token);
}
