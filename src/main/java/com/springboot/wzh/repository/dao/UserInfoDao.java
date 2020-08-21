package com.springboot.wzh.repository.dao;

import com.springboot.wzh.domain.UserInfo;

import javax.annotation.Resource;

@Resource
public interface UserInfoDao {
  UserInfo getUserInfoEntityByUserName(String userName);
}
