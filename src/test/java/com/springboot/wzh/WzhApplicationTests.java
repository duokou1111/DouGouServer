package com.springboot.wzh;

import com.springboot.wzh.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
class WzhApplicationTests {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Test
    void contextLoads() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/test"));
    }

}
