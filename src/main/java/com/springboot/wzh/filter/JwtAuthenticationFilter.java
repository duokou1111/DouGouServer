package com.springboot.wzh.filter;

import com.springboot.wzh.utils.JwtUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
       String token = httpServletRequest.getHeader("token");
       String username = JwtUtils.getUsername(token);
       if(StringUtils.isEmpty(token)){
           throw new NullPointerException("Token为空");
       }
       if(!token.equals(redisTemplate.opsForValue().get(username))){
           throw new Exception("Token不匹配");
       }
       filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
