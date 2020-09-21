package com.springboot.wzh.model;

import com.springboot.wzh.utils.Img;
import org.springframework.web.multipart.MultipartFile;

public class StreamCoverVO {
    @Img()
    private MultipartFile coverImg;

    public MultipartFile getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(MultipartFile coverImg) {
        this.coverImg = coverImg;
    }
}
