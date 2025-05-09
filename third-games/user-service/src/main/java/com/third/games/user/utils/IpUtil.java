package com.third.games.user.utils;


import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    /**
     * 获取客户端的真实IP地址
     *
     * @param request HttpServletRequest 请求对象
     * @return 客户端真实IP地址
     */
    public static String getRealIp(HttpServletRequest request) {
        // 获取客户端真实 IP 地址的头信息
        String ip = request.getHeader("X-Forwarded-For");

        // 如果存在多个 IP 地址，`X-Forwarded-For` 头中的 IP 会是由逗号隔开的
        // 取第一个 IP
        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            // `X-Forwarded-For` 可能是多个代理服务器的 IP，取第一个 IP 即可
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            return ip;
        }

        // 如果没有通过 `X-Forwarded-For` 获取到，尝试使用代理服务器的其它头部字段
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        // 最后，获取远程地址
        return request.getRemoteAddr();
    }
}
