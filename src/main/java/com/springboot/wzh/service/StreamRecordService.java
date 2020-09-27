package com.springboot.wzh.service;

import com.springboot.wzh.domain.StreamRecord;
import com.springboot.wzh.repository.dao.StreamRecordDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StreamRecordService {
    @Resource
    StreamRecordDao streamRecordDao;
    public void onLive(StreamRecord streamRecord){
        streamRecordDao.onLive(streamRecord);
    }
    public void downLive(StreamRecord streamRecord){
        streamRecordDao.onDown(streamRecord);
    }
}
