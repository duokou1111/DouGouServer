package com.springboot.wzh.repository.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Resource
public interface GlobalConfigDao {
    @Select("select VALUE from GLOBAL_CONFIG where VARIABLE = #{variable}")
    String getVariable(String variable);
}
