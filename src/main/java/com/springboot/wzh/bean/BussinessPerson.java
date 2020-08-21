package com.springboot.wzh.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
@Component
public class BussinessPerson implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {
    private Dog dog;
    BussinessPerson(){
        System.out.println("调用初始化方法");
    }
    @Autowired
    public void setAnimal(Dog dog){
        System.out.println("延迟依赖注入");
        this.dog = dog;
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(this.getClass().getSimpleName()+"：调用setBeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println(this.getClass().getSimpleName()+"：调用setBeanName");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass().getSimpleName()+"：调用destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getSimpleName()+"：调用afterPropertySet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(this.getClass().getSimpleName()+"：调用setApplicationContext");
    }
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+"：注解postConstruct调用初始化");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println(this.getClass().getSimpleName()+"：注解preDestroy调用析构方法");
    }
}
