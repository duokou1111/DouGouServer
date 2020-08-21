package com.springboot.wzh.filter;
import com.springboot.wzh.model.UserDetails;
import com.springboot.wzh.utils.JwtUtils;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Logger
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       Authentication authentication = new UsernamePasswordAuthenticationToken("wuzihan","qtwzh1999");
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        logger.info(userDetails.getUsername()+"登录成功");
        String token = JwtUtils.generateJsonWebToken(userDetails.getUserInfo());
        response.setHeader("token",token);
    }


}
