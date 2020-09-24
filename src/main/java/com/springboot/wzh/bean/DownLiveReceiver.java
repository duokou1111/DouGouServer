package com.springboot.wzh.bean;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "down")
public class DownLiveReceiver {

    @RabbitHandler
    public void handler(String message){
        System.out.println("接收消息："+message);
    }

}