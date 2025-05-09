package com.third.games.captcha.config;

import com.third.games.captcha.filter.IpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {
    @Bean
    public FilterRegistrationBean<IpFilter> loggingFilter() {
        FilterRegistrationBean<IpFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IpFilter());
        bean.addUrlPatterns("/api/*");
        bean.setOrder(0);
        return bean;
    }
}
