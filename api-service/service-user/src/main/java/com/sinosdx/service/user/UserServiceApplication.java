package com.sinosdx.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wendy
 * @date 2021/02/24
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.sinosdx.service.user.dao.mapper")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
