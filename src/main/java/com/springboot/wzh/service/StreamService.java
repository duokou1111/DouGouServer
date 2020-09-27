package com.springboot.wzh.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;
import com.springboot.wzh.model.StreamInfoDTO;
import com.springboot.wzh.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class StreamService {
    private static final String REDIS_PREFIX = "STREAM:";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ClassificaionTagService classificaionTagService;
    public StreamInfoDTO getStreamInfo(String roomId){
        RedisStreamSettings redisStreamSettings = JSONObject.parseObject((String) redisTemplate.opsForValue().get(REDIS_PREFIX+roomId),RedisStreamSettings.class);
        if (redisStreamSettings == null){
            return null;
        }
        String username = redisStreamSettings.getUsername();
        UserDetails userDetails = userInfoService.getUserDetails(username);
        StreamInfoDTO streamInfoDTO = new StreamInfoDTO();
        streamInfoDTO.setHeat(1321321);
        streamInfoDTO.setRedisStreamSettings(redisStreamSettings);
        streamInfoDTO.setSubcribeNum(321321);
        streamInfoDTO.setTagName(classificaionTagService.getTagNameById(redisStreamSettings.getTagId()));
        return streamInfoDTO;
    }
}
