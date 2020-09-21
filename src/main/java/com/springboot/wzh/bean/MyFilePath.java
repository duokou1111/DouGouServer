package com.springboot.wzh.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="myfile.path")
public class MyFilePath {
    private String coverImgPath;

    public String getCoverImgPath() {
        return coverImgPath;
    }

    public void setCoverImgPath(String coverImgPath) {
        this.coverImgPath = coverImgPath;
    }
}
