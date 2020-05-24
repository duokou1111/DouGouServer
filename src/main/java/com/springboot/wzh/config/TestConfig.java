package com.springboot.wzh.config;

import com.springboot.wzh.model.JDBCBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfig {
    @Scope
    @Bean(name = "JDBCBean")
    public JDBCBean getJdbc(){
        JDBCBean jdbcBean = new JDBCBean();
       jdbcBean.init(Double.toString(Math.random()),"1","1","1");
        return jdbcBean;
    }
}
