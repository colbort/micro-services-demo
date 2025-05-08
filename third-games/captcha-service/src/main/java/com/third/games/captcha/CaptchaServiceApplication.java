package com.third.games.captcha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.third.games.common.mapper")
public class CaptchaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaServiceApplication.class, args);
    }

}
