package com.third.games.user;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.third.games.common.mapper")
@ComponentScan(basePackages = {
        "com.third.games.user",
        "com.third.games.common"
})
@EnableFeignClients(basePackages = "com.third.games.user.feign")
@EnableDiscoveryClient
@EnableAutoDataSourceProxy  // 开启数据源代理
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
