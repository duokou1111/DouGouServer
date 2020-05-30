package com.springboot.wzh.controller;

import com.springboot.wzh.repository.TestRepository;
import com.springboot.wzh.repository.mapper.TestMapper;
import com.springboot.wzh.repository.dao.TestDao;
import com.springboot.wzh.service.IndexService;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class IndexController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IndexService indexService;
    @Autowired
    TestDao testDao;
    @Autowired
    TestRepository testRepository;
    @Resource
    TestMapper testMapper;
    @RequestMapping("/index")
    @Cacheable(value="ppp")
    @ResponseBody
    public String index(String name){
        redisTemplate.opsForValue().set("test","~~~");
        redisTemplate.opsForValue().set("test::a","~~~");
        System.out.println("没有走缓存？");
        return "Hello"+name;
    }
    @RequestMapping("/index2")
    @CachePut(value="pp2")
    @ResponseBody
    public String index2(String name){
        return "HeeloWorld";
    }
}
