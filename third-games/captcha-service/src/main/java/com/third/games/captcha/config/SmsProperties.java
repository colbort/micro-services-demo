package com.third.games.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private String appKey;
    private String secret;
    private Limit limit = new Limit();

    @Data
    public static class Limit {
        private int intervalSeconds;
        private int maxPerDay;
    }
}

