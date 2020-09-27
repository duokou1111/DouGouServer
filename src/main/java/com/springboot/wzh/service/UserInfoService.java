package com.springboot.wzh.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.UserInfo;
import com.springboot.wzh.model.UserDetails;
import com.springboot.wzh.repository.dao.UserInfoDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoService {
    private static final String REDIS_PREFIX = "TOKEN:";
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RedisTemplate redisTemplate;
    public UserInfo getUserInfo(String userName){
       return userInfoDao.getUserInfoEntityByUserName(userName);
    }
    public UserDetails getUserDetails(String username){
        return JSONObject.parseObject((String)redisTemplate.opsForValue().get(REDIS_PREFIX+username),UserDetails.class);
    }
}
