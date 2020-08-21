package com.springboot.wzh.config;

import com.springboot.wzh.filter.JwtAuthenticationFilter;
import com.springboot.wzh.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtUserDetailService jwtUserDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.csrf().disable()
        http.authorizeRequests().
                antMatchers("/resources/static/**","**").permitAll().
                and().formLogin().and().csrf().disable().addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

}
