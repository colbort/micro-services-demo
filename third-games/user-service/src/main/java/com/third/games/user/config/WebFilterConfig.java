package com.third.games.user.config;

import com.third.games.user.filter.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFilterConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }

//    @Bean(name = "userTokenFilter")
//    public FilterRegistrationBean<TokenFilter> tokenFilter() {
//        FilterRegistrationBean<TokenFilter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new TokenFilter());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(1);
//        return bean;
//    }

//    @Bean
//    public FilterRegistrationBean<IpFilter> loggingFilter() {
//        FilterRegistrationBean<IpFilter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new IpFilter());
//        bean.addUrlPatterns("/api/*");
//        bean.setOrder(0);
//        return bean;
//    }
}
