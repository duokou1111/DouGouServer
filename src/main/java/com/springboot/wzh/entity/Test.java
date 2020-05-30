package com.springboot.wzh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="test")
public class Test {
    @Id
    @Column
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
    @Override
    public String toString(){
        return this.test;
    }
}
