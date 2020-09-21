package com.springboot.wzh.bean;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class OnLiveReceiver {
    public void onLive(String roomId){

    }
}
