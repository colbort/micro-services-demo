package com.third.games.captcha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.third.games.common.mapper")
@ComponentScan(basePackages = {
        "com.third.games.captcha",
        "com.third.games.common"
})
@EnableDiscoveryClient
public class CaptchaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaServiceApplication.class, args);
    }

}
