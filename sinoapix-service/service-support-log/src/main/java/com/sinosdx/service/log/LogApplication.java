package com.sinosdx.service.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@MapperScan("com.sinosdx.service.log.dao.mapper")
@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
