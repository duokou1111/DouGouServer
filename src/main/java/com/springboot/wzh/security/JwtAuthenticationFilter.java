package com.springboot.wzh.security;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.model.ActionResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/tag/**", "GET"));
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = httpServletRequest.getHeader("token");
        if (token == null) {
            throw new InternalAuthenticationServiceException("Failed to get the token");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null,token);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        ActionResult actionResult = new ActionResult();
        actionResult.setMessage("Token验证失败");
        String str = JSONObject.toJSONString(actionResult);
        response.setStatus(401);//未授权
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}



