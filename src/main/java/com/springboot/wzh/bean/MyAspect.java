package com.springboot.wzh.bean;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class MyAspect {
    @DeclareParents(value = "com.springboot.wzh.bean.UserService",defaultImpl = Validator.class)
    public IValidator iValidator;
    @Pointcut("execution(* com.springboot.wzh.bean.UserService.printUser(..))")
    public void pointCut(){
        System.out.println("pointCut?");
    }
    @Before("pointCut() && args(name)")
    public void before(JoinPoint joinpoint, String name){
        Object[] args = joinpoint.getArgs();
        System.out.println("before");
        System.out.println("before:name:"+name);
        System.out.println(args[0]);
    }
    @After("pointCut()")
    public void After(){
        System.out.println("After");
    }
    @AfterReturning("pointCut()")
    public void AfterReturning(){
        System.out.println("After returning");
    }
    @AfterThrowing("pointCut()")
    public void AfterThrowing(){
        System.out.println("AfterThrowing");
    }
}
