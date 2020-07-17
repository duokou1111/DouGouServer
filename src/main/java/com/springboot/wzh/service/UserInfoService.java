package com.springboot.wzh.service;

import com.springboot.wzh.model.UserInfo;
import com.springboot.wzh.repository.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    public UserInfo getUserInfo(String userName){
       return userInfoDao.getUserInfoEntityByUserName(userName);
    }
}