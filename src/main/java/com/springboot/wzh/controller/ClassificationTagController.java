package com.springboot.wzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.service.ClassificaionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tag")
public class ClassificationTagController {
    @Autowired
    ClassificaionTagService classificaionTagService;
    @GetMapping("/all")
    @ResponseBody
    public String getAllTags(){
        System.out.println("get tags");
        return JSONObject.toJSONString(classificaionTagService.getAll());
    }
}
