package com.springboot.wzh.bean;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;

import com.springboot.wzh.domain.StreamRecord;
import com.springboot.wzh.service.StreamRecordService;
import lombok.extern.slf4j.Slf4j;
import com.springboot.wzh.model.UserDetails;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "live")
@Slf4j
public class OnLiveReceiver {
    @Autowired
    private RedisTemplate redisTemplate;
    private static String REDIS_PREFIX = "TOKEN:";
    @Autowired
    private ElasticSearchUtils elasticSearchUtils;
    @Autowired
    private StreamRecordService streamRecordService;
    @RabbitHandler
    public void handler(String message) throws IOException {
        System.out.println("Receive a message");
        RedisStreamSettings redisStreamSettings = JSONObject.parseObject(message,RedisStreamSettings.class);
        if (redisStreamSettings == null || redisStreamSettings.getSecret() == null){
            throw new RuntimeException("Json格式化错误");
        }
        UserDetails userDetails = JSONObject.parseObject((String)redisTemplate.opsForValue().get(REDIS_PREFIX+redisStreamSettings.getUsername()),UserDetails.class);
        StreamRecord streamRecord = new StreamRecord();
        streamRecord.setCover(redisStreamSettings.getCoverPath());
        streamRecord.setSecret(redisStreamSettings.getSecret());
        streamRecord.setTagId(redisStreamSettings.getTagId());
        streamRecord.setUserId(userDetails.getUserInfo().getId());
        streamRecord.setTitle(redisStreamSettings.getTitle());
        elasticSearchUtils.addDocument(redisStreamSettings.getSecret(),message);
        streamRecordService.onLive(streamRecord);
    }

}