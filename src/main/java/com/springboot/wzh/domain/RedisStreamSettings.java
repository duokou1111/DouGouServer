package com.springboot.wzh.domain;

import com.springboot.wzh.model.StreamSettingsVO;

import java.io.Serializable;

public class RedisStreamSettings extends StreamSettingsVO  implements Serializable {
    private String secret;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
