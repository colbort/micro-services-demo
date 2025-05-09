package com.third.games.user.filter;

import com.third.games.user.utils.IpUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化配置
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = IpUtil.getRealIp(httpRequest);
        httpRequest.setAttribute("realIp", ip);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 资源清理
    }
}

