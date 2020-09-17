package com.springboot.wzh.filter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.model.ActionResult;
import com.springboot.wzh.model.UserDetails;
import com.springboot.wzh.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private RedisTemplate redisTemplate;
    private static String REDIS_PREFIX = "TOKEN:";
    public JwtAuthorizationFilter() {
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String principal = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal,password);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        logger.info(userDetails.getUsername()+"登录成功");
        String token = JwtUtils.generateJsonWebToken(userDetails.getUserInfo());
        response.setHeader("token",token);
        response.setStatus(200);
        long daySeconds = (long) (86400 + Math.random()*300);
        redisTemplate.opsForValue().set(REDIS_PREFIX+userDetails.getUsername(),token,daySeconds, TimeUnit.SECONDS);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        ActionResult actionResult = new ActionResult();
        actionResult.setMessage(failed.getMessage());
        String str = JSONObject.toJSONString(actionResult);
        response.setStatus(500);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
