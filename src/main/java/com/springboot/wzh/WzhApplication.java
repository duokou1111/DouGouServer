package com.springboot.wzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

@SpringBootApplication
@MapperScan("com.springboot.wzh.repository.dao")
@EnableCaching
@EnableWebSecurity
public class WzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzhApplication.class, args);
    }

}
