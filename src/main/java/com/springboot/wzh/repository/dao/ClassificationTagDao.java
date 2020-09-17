package com.springboot.wzh.repository.dao;

import com.springboot.wzh.model.ClassificationTag;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ClassificationTagDao {
    @Select("select id,name,pid from CLASSIFICATION_TAG")
    List<ClassificationTag> getAll();
}
