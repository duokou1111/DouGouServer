package com.springboot.wzh.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@SpringBootConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.csrf().disable()
        http.authorizeRequests().
                antMatchers("/resources/static/**","/user/auth").permitAll().
                and().formLogin().and()//开启formLogin默认配置
                .csrf().disable();

    }
}
