package com.third.games.common.service.impl;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.hutool.json.JSONObject;
import com.third.games.common.config.JwtProperties;
import com.third.games.common.security.LoginUser;
import com.third.games.common.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService implements TokenService {
    @Autowired
    private JwtProperties properties;

    @Override
    public String createToken(LoginUser loginUser, String deviceType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", loginUser.getUserId());
        payload.put("username", loginUser.getUsername());

        return SaJwtUtil.createToken(
                properties.getLoginType(),
                loginUser.getUserId(),
                deviceType,
                properties.getTimeout(),
                payload,
                properties.getSecretKey()
        );
    }

    @Override
    public LoginUser parseToken(String token) {
        try {
            JSONObject payload = SaJwtUtil.getPayloads(token, properties.getLoginType(), properties.getSecretKey());
            Long userId = payload.getLong("userId");
            String username = payload.getStr("username");

            if (userId == null || username == null) {
                throw new RuntimeException("Token字段缺失");
            }
            return new LoginUser(userId, username);
        } catch (Exception e) {
            throw new RuntimeException("Token解析失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            SaJwtUtil.parseToken(token, properties.getLoginType(), properties.getSecretKey(), true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

