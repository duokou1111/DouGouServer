package com.springboot.wzh.repository.dao;

import com.springboot.wzh.model.UserInfo;

import javax.annotation.Resource;

@Resource
public interface UserInfoDao {
  UserInfo getUserInfoEntityByUserName(String userName);
}
