package com.springboot.wzh.bean;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;
import com.springboot.wzh.domain.StreamRecord;
import com.springboot.wzh.service.StreamRecordService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "disconnect")
public class DownLiveReceiver {
    @Autowired
    private StreamRecordService streamRecordService;
    @Autowired
    private ElasticSearchUtils elasticSearchUtils;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "STREAM:";
    @RabbitHandler
    public void handler(String message) throws IOException {
        System.out.println("message = " + message);
        RedisStreamSettings redisStreamSettings = JSONObject.parseObject(message,RedisStreamSettings.class);
        if (redisStreamSettings == null || redisStreamSettings.getSecret() == null){
            throw new RuntimeException("Json格式化错误");
        }
        redisTemplate.watch(REDIS_PREFIX+redisStreamSettings.getRoomId());
        RedisStreamSettings redis = JSONObject.parseObject((String)redisTemplate.opsForValue().get(REDIS_PREFIX+redisStreamSettings.getRoomId()),RedisStreamSettings.class);
        if (redis == null || redis.getDisconnectDate().longValue() != redisStreamSettings.getDisconnectDate().longValue()){
            return;
        }
        boolean flag;
        if (System.currentTimeMillis() - redis.getDisconnectDate() >= 580000 ){
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    redisOperations.delete(REDIS_PREFIX+redisStreamSettings.getRoomId());
                    try {
                        redisOperations.exec();
                        return  true;
                    }catch (Exception e){
                        System.out.println(e);
                        return false;
                    }
                }
            };
            flag = (boolean) redisTemplate.execute(sessionCallback);
            if (flag == true){
                StreamRecord streamRecord = new StreamRecord();
                streamRecord.setSecret(redisStreamSettings.getSecret());
                elasticSearchUtils.deleteDocument(redisStreamSettings.getSecret());
                streamRecordService.downLive(streamRecord);
            }

        }

    }

}