package com.springboot.wzh.repository;

import com.springboot.wzh.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {
    public Test findByTest(String test);
   List<Test> findFirst10ByTest(String test);
   /* public List<Test> findAll();*/
}
