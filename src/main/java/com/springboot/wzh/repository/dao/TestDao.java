package com.springboot.wzh.repository.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Resource
public interface TestDao {
    public String getTest();
}
