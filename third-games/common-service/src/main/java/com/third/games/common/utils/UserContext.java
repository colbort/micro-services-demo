package com.third.games.common.utils;

import com.third.games.common.security.LoginUser;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    public void set(LoginUser user) {
        THREAD_LOCAL.set(user);
    }

    public LoginUser get() {
        return THREAD_LOCAL.get();
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }
}

