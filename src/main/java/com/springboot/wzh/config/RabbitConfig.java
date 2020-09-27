package com.springboot.wzh.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue directLiveQueue(){
        return new Queue("live",true); //队列名字，是否持久化
    }
    @Bean
    public Queue directDownQueue(){return new Queue("disconnect",true);}
}
