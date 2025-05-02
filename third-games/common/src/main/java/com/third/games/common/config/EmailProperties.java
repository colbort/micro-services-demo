package com.third.games.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
//import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@Component
//@RefreshScope
@ConfigurationProperties(prefix = "sms")
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

