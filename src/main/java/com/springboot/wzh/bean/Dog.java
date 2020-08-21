package com.springboot.wzh.bean;

import org.springframework.stereotype.Component;

@Component
public class Dog {
    private String name;
    public void bark(){
        System.out.println("The dog who named "+name+" is barking");
    }
    Dog(){
        this.name = "嘟嘟";
    }
}
