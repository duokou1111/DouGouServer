package com.springboot.wzh.controller;

import com.springboot.wzh.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/auth")
    @ResponseBody
    public String auth(){
        return userInfoService.getUserInfo("wuzihan").getUserName();
    }
}
