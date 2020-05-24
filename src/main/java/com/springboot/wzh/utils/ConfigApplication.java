package com.springboot.wzh.utils;

import com.springboot.wzh.config.TestConfig;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigApplication {
    private static AnnotationConfigApplicationContext context;
    public static AnnotationConfigApplicationContext getConfig(){
        if(context==null)
             context = new AnnotationConfigApplicationContext(TestConfig.class);
        return context;
    }
}
