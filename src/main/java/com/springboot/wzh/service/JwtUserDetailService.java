package com.springboot.wzh.service;

import com.springboot.wzh.domain.UserInfo;
import com.springboot.wzh.repository.dao.UserInfoDao;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class JwtUserDetailService implements UserDetailsService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDao.getUserInfoEntityByUserName(username);
        if (null == userInfo){
            throw new BadCredentialsException("帐号不存在，请重新输入");
        }
        com.springboot.wzh.model.UserDetails userDetails = new com.springboot.wzh.model.UserDetails();
        userDetails.setUsername(userInfo.getUserName());
        userDetails.setUserInfo(userInfo);
        userDetails.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        userDetails.addAuth(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return Integer.toString(userInfo.getRole());
            }
        });
        return userDetails;
    }
}
