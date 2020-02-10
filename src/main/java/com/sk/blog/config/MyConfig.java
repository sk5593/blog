package com.sk.blog.config;

import com.sk.blog.bean.Visitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MyConfig {

    @Bean
    public ExecutorService getService()
    {
        return Executors.newCachedThreadPool();
    }
    @Bean
    public List<Visitor> getList()
    {
        List<Visitor> list = new ArrayList();
        return Collections.synchronizedList(list);
    }
}
