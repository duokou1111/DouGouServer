package com.springboot.wzh.bean;

import org.springframework.stereotype.Component;

@Component
public interface IValidator {
    public boolean validate(String name);
}
