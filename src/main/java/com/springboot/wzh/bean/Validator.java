package com.springboot.wzh.bean;

import org.springframework.stereotype.Component;

@Component
public class Validator implements IValidator{

    @Override
    public boolean validate(String name) {
        return name.equals("WuZihan");
    }
}
