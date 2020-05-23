package com.springboot.wzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

@SpringBootApplication
public class WzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzhApplication.class, args);
    }

}
