package com.springboot.wzh.bean;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void printUser(String name){
        System.out.println(name);
    }
}
