package com.springboot.wzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.bean.MyFilePath;
import com.springboot.wzh.domain.RedisStreamSettings;
import com.springboot.wzh.model.ActionResult;
import com.springboot.wzh.model.StreamCoverVO;
import com.springboot.wzh.model.StreamSettingsVO;
import com.springboot.wzh.utils.Common;
import com.springboot.wzh.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/stream")
public class StreamController {
    private static final String REDIS_PREFIX = "STREAM:";
    private static final String ON_LIVE_REDIS_PREFIX = "ONLIVE:";
    @Autowired
    MyFilePath myFilePath;
    @Autowired
    RedisTemplate redisTemplate;
    @PutMapping("/settings")
    @ResponseBody
    public String setStreamSettings(@Valid StreamSettingsVO streamSetting, BindingResult bindingResult,HttpServletRequest request) throws IOException {
        ActionResult actionResult = new ActionResult();
      if(bindingResult.hasErrors()){
          actionResult.setMessage(bindingResult.getFieldError().getDefaultMessage());
          actionResult.setSuccess(false);
          return JSONObject.toJSONString(actionResult);
      }
        redisTemplate.watch(ON_LIVE_REDIS_PREFIX+streamSetting.getRoomId());
        if(redisTemplate.hasKey(ON_LIVE_REDIS_PREFIX+streamSetting.getRoomId())){
            actionResult.setSuccess(false);
            actionResult.setMessage("房号已经被人占用");
            return JSONObject.toJSONString(actionResult);
        }
        RedisStreamSettings redisStreamSettings = new RedisStreamSettings();
        redisStreamSettings.setCoverPath(streamSetting.getCoverPath());
        redisStreamSettings.setRoomId(streamSetting.getRoomId());
        redisStreamSettings.setUsername(JwtUtils.getUsername(request.getHeader("token")));
        redisStreamSettings.setTagId(streamSetting.getTagId());
        redisStreamSettings.setTitle(streamSetting.getTitle());
        redisStreamSettings.setSecret(Common.generateStreamSecret(JwtUtils.getUsername(request.getHeader("token"))));
        ActionResult finalActionResult = actionResult;
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                //“setIfAbsent return  null when used in transaction”(这不坑爹吗，我想了一整天都没想明白boolean类型给我一个null)
                redisOperations.opsForValue().setIfAbsent(REDIS_PREFIX+streamSetting.getRoomId(),JSONObject.toJSONString(redisStreamSettings),600L, TimeUnit.SECONDS);
                try {
                    List<Object> list = redisOperations.exec();
                    if(list.get(0).toString().equals("true")){
                        finalActionResult.setSuccess(true);
                        Map<String,Object> hashMap = new HashMap<>();
                        hashMap.put("settings",redisStreamSettings);
                        finalActionResult.setData(hashMap);
                    }else{
                        finalActionResult.setSuccess(false);
                        finalActionResult.setMessage("房号已经被人占用");
                    }
                    return finalActionResult;
                }catch (Exception e){
                    //被监视的Key有变动
                    finalActionResult.setSuccess(false);
                    finalActionResult.setMessage("房号已经被人占用");
                    return finalActionResult;
                }

            }
        };
        actionResult = (ActionResult) redisTemplate.execute(sessionCallback);
        return JSONObject.toJSONString(actionResult);
    }
    @PutMapping("/coverImg")
    @ResponseBody
    public String getCoverImg(@Valid StreamCoverVO streamCoverVO, BindingResult bindingResult, HttpServletRequest request) throws IOException {
        String fileName = Common.generateFileName(request.getHeader("token"));
        int position = streamCoverVO.getCoverImg().getOriginalFilename().lastIndexOf(".");
        String suffix = streamCoverVO.getCoverImg().getOriginalFilename().substring(position);
        File file = new File(myFilePath.getCoverImgPath()+"/"+fileName+suffix);
        System.out.println("myFilePath.getCoverImgPath() = " + myFilePath.getCoverImgPath());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(streamCoverVO.getCoverImg().getInputStream().readAllBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        ActionResult actionResult = new ActionResult();
        actionResult.setSuccess(true);
        actionResult.setMessage(fileName+suffix);
        return JSONObject.toJSONString(actionResult);
    }
}
