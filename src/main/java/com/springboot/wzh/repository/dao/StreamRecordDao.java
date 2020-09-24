package com.springboot.wzh.repository.dao;

import org.apache.ibatis.annotations.Insert;

import javax.annotation.Resource;

@Resource
public interface StreamRecordDao {
   /* @Update("Insert into STREAM_RECORD(user_id,tag_id,title,cover,end_date,secret) values(#{userId},#{tagId},#{title},#{cover},#{endDate},#{secret})")
    void onLive(String variable);*/
}
