package com.springboot.wzh.model;

import com.springboot.wzh.domain.RedisStreamSettings;

public class StreamInfoDTO {
    private RedisStreamSettings redisStreamSettings;
    private String tagName;
    private Integer heat;
    private String sculpt;
    private Integer subcribeNum;

    public RedisStreamSettings getRedisStreamSettings() {
        return redisStreamSettings;
    }

    public void setRedisStreamSettings(RedisStreamSettings redisStreamSettings) {
        this.redisStreamSettings = redisStreamSettings;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public String getSculpt() {
        return sculpt;
    }

    public void setSculpt(String sculpt) {
        this.sculpt = sculpt;
    }

    public Integer getSubcribeNum() {
        return subcribeNum;
    }

    public void setSubcribeNum(Integer subcribeNum) {
        this.subcribeNum = subcribeNum;
    }
}
