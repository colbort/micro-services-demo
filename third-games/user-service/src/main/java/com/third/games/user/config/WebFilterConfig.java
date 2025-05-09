package com.third.games.user.config;

import com.third.games.user.filter.IpFilter;
import com.third.games.user.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean(name = "userTokenFilter")
    public FilterRegistrationBean<TokenFilter> tokenFilter() {
        FilterRegistrationBean<TokenFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TokenFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<IpFilter> loggingFilter() {
        FilterRegistrationBean<IpFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IpFilter());
        bean.addUrlPatterns("/api/*");
        bean.setOrder(0);
        return bean;
    }
}
