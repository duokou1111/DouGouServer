package com.springboot.wzh.controller;

import com.springboot.wzh.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    IndexService indexService;
    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/index")
    @ResponseBody
    public void index(){
        System.out.println(indexService.getUser("wuzihan").toString());
        System.out.println(indexService.getConfig("login_limit"));
    }
}
