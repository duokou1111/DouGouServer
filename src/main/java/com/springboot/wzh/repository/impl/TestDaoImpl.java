package com.springboot.wzh.repository.impl;

import com.springboot.wzh.repository.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements TestDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public String getTest() {
        return jdbcTemplate.queryForMap("select * from test").toString();
    }
}
