package com.springboot.wzh.security;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

public class JwtProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static String REDIS_PREFIX = "TOKEN:";
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        com.springboot.wzh.model.UserDetails userDetails = new com.springboot.wzh.model.UserDetails();
        String requestToken = (String) usernamePasswordAuthenticationToken.getCredentials();
        if(StringUtils.isEmptyOrWhitespace(requestToken)){
            userDetails.setEnabled(false);
            userDetails.setAccountNonExpired(false);
            return userDetails;
        }
        String username =  JwtUtils.getUsername(requestToken);
        String redisUserInfo = (String) redisTemplate.opsForValue().get(REDIS_PREFIX+username);
        userDetails = JSONObject.parseObject(redisUserInfo,com.springboot.wzh.model.UserDetails.class);
        if (!requestToken.equals(userDetails.getToken())){
            return null;
        }
        if(JwtUtils.isExpiration(requestToken)){
            userDetails.setAccountNonExpired(false);
            userDetails.setEnabled(false);
            return userDetails;
        }
        return userDetails;

    }
}
