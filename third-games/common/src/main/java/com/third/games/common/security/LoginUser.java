package com.third.games.common.security;

import lombok.Getter;

@Getter
public class LoginUser {
    private final Long userId;
    private final String username;

    public LoginUser(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}

