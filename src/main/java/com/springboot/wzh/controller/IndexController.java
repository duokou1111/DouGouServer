package com.springboot.wzh.controller;

import com.springboot.wzh.model.ActionResult;
import com.springboot.wzh.model.JDBCBean;
import com.springboot.wzh.service.IndexService;
import com.springboot.wzh.utils.ConfigApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    IndexService indexService;
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
       return indexService.getTest();
    }
}
