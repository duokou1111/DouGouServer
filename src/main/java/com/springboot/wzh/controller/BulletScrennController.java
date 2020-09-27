package com.springboot.wzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.model.BulletScreen;
import com.springboot.wzh.utils.JwtUtils;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BulletScrennController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    private static final String TOPIC = "/receiveBulletScreen";
    @MessageMapping("/bulletScreen")
    public void receive(Message message){
        byte[] payload = (byte[]) message.getPayload();
        String content = new String(payload);
        System.out.println("content = " + content);
        BulletScreen bulletScreen = JSONObject.parseObject(content,BulletScreen.class);
        bulletScreen.setUsername(JwtUtils.getUsername(bulletScreen.getToken()));
        broadcast(bulletScreen.getRoomId(),JSONObject.toJSONString(bulletScreen));
    }

    public void broadcast(int roomId,String message){
        simpMessagingTemplate.convertAndSend(TOPIC+"/"+roomId,message);
    }
}
