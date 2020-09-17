package com.springboot.wzh.service;

import com.springboot.wzh.model.ClassificationTag;
import com.springboot.wzh.repository.dao.ClassificationTagDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassificaionTagService {
    @Resource
    private ClassificationTagDao classificationTagDao;
    public List<ClassificationTag> getAll(){
        return classificationTagDao.getAll();
    }
}
