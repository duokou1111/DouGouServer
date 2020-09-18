package com.springboot.wzh.config;

import com.springboot.wzh.security.CustomizeUsernamePasswordAuthenticationFilter;
import com.springboot.wzh.security.JwtAuthenticationFilter;
import com.springboot.wzh.security.JwtProvider;
import com.springboot.wzh.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@SpringBootConfiguration
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtUserDetailService jwtUserDetailService;
    @Autowired
    @Lazy
    AuthenticationManager authenticationManagerBean;
    @Autowired
    @Lazy
    JwtProvider jwtProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable()
        http.cors().configurationSource(CorsConfigurationSource());
        http.authorizeRequests().
                antMatchers("/resources/static/**").permitAll().
                and().formLogin().and().csrf().disable().addFilterAt(jwtAuthenticationFilter(),CustomizeUsernamePasswordAuthenticationFilter.class);
    }
    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
        corsConfiguration.addExposedHeader("token");
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public JwtProvider jwtProvider(){
        return new JwtProvider();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    @DependsOn("authenticationManagerBean")
    public CustomizeUsernamePasswordAuthenticationFilter jwtAuthorizationFilter() throws Exception {
        CustomizeUsernamePasswordAuthenticationFilter jwtAuthorizationFilter = new CustomizeUsernamePasswordAuthenticationFilter();
        jwtAuthorizationFilter.setAuthenticationManager(authenticationManagerBean);
        return jwtAuthorizationFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean("JwtAuthenticationFilter")
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        ProviderManager providerManager =
                new ProviderManager(Collections.singletonList(jwtProvider));
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(providerManager);
        return jwtAuthenticationFilter;
    }
}
