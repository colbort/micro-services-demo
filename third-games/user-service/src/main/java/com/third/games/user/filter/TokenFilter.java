package com.third.games.user.filter;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.UserContext;
import com.third.games.user.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserContext userContext;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws IOException, ServletException {

        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handlerMethod != null) {
            if (handlerMethod.getMethod().isAnnotationPresent(NoAuth.class)
                    || handlerMethod.getBeanType().isAnnotationPresent(NoAuth.class)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 从 Header 获取 Token
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            try {
                LoginUser loginUser = tokenService.parseToken(token);
                userContext.set(loginUser); // 保存到线程上下文
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing token");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            userContext.clear();
        }
    }
}

