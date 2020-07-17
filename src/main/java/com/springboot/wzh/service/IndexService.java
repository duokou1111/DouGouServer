package com.springboot.wzh.service;

import com.springboot.wzh.model.UserInfo;
import com.springboot.wzh.repository.dao.GlobalConfigDao;
import com.springboot.wzh.repository.dao.UserInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexService {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private GlobalConfigDao globalConfigDao;
    public UserInfo getUser(String username){
        return userInfoDao.getUserInfoEntityByUserName(username);
    }
    public String getConfig(String name){
        return  globalConfigDao.getVariable("Login_limit");
    }

}
