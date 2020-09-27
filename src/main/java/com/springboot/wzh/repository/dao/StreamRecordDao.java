package com.springboot.wzh.repository.dao;

import com.springboot.wzh.domain.StreamRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.annotation.Resource;

@Resource
public interface StreamRecordDao {
    @Insert("Insert into STREAM_RECORD(user_id,tag_id,title,cover,start_date,secret) values(#{userId},#{tagId},#{title},#{cover},NOW(),#{secret})")
    void onLive(StreamRecord streamRecord);
    @Update("Update STREAM_RECORD set end_date =  NOW() where secret = #{secret}")
    void onDown(StreamRecord streamRecord);
}
