package com.springboot.wzh.filter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.model.ActionResult;
import com.springboot.wzh.model.UserDetails;
import com.springboot.wzh.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String principal = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("principal = " + principal);
        System.out.println("password = " + password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal,password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        logger.info(userDetails.getUsername()+"登录成功");
        String token = JwtUtils.generateJsonWebToken(userDetails.getUserInfo());
        response.setHeader("token",token);
        response.setStatus(200);
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
