package com.third.games.common.filter;

import com.third.games.common.utils.IpUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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

