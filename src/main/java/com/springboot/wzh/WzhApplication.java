package com.springboot.wzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

@SpringBootApplication
@EnableCaching
@MapperScan("com.springboot.wzh.repository.Mapper")
public class WzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzhApplication.class, args);
    }

}
