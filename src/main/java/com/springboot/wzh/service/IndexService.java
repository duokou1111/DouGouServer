package com.springboot.wzh.service;

import com.springboot.wzh.repository.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndexService {
    @Autowired
    TestDao testDao;

    public String getTest(){
       /*return testDao.getTest();*/
        return getTest2();
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String getTest2(){
        return testDao.getTest();
    }
}
