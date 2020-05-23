package com.springboot.wzh.controller;

import com.springboot.wzh.model.ActionResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public ActionResult index(){
        ActionResult actionResult = new ActionResult();
        actionResult.setMessage("this is HelloWorld");
        return actionResult;
    }
}
