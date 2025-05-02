package com.third.games.common.utils;

import com.third.games.common.security.LoginUser;

public class UserContext {

    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(LoginUser user) {
        THREAD_LOCAL.set(user);
    }

    public static LoginUser get() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}

