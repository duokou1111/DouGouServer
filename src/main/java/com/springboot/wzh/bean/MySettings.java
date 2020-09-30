package com.springboot.wzh.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="settings")
public class MySettings {
    private String cors;

    public String getCors() {
        return cors;
    }

    public void setCors(String cors) {
        this.cors = cors;
    }
}
