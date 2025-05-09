package com.third.games.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String host;
    private Integer port;
    private Boolean auth;
    private Boolean starttlsEnable;
    private String from;
    private String password;
    private Limit limit = new Limit();

    @Data
    public static class Limit {
        private int intervalSeconds;
        private int maxPerDay;
    }
}

