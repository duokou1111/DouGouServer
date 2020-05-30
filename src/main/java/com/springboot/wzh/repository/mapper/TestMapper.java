package com.springboot.wzh.repository.mapper;

import com.springboot.wzh.entity.Test;
import org.apache.ibatis.annotations.Select;


public interface TestMapper {
    public Test getTest();
    @Select("SELECT * FROM test")
    public Test getTest2();
}
