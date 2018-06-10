package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;


public class Consumer {
    public static void main(String[] args) {

        //测试常规服务
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer2 start");
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println("consumer2");
        Map<String, String> m=new HashMap<String, String>();
        m.put("Tony", "123");
        System.out.println(demoService.login(m));
    }
}
