package com.third.games.user.filter;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.exception.BizException;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.JwtTokenUtil;
import com.third.games.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserContext userContext;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 判断是否是 HandlerMethod（只处理 Controller 方法）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 判断是否加了 @NoAuth 注解（类或方法上）
        if (handlerMethod.getMethod().isAnnotationPresent(NoAuth.class)
                || handlerMethod.getBeanType().isAnnotationPresent(NoAuth.class)) {
            return true;
        }

        // 读取 token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            throw new BizException(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
        }

        try {
            LoginUser loginUser = tokenUtil.parseToken(token);
            userContext.set(loginUser); // 设置上下文
            return true;
        } catch (Exception e) {
            throw new BizException(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        userContext.clear(); // 清除上下文
    }
}
