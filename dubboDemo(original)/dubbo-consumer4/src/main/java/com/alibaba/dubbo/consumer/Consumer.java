package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
public class Consumer {
    @Autowired
    public   DemoService demoService;

    @RequestMapping(value = "/test")
    public String test() {

        //测试常规服务
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer4 start");
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println("consumer4");
        System.out.println(demoService.getPermissions(1L));
        return  "hello";
    }

    @RequestMapping(value = {"/test/login"}, method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Map<String, String> map) {
        System.out.println("consumer4 start");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer4 start");
        demoService = context.getBean(DemoService.class);
        System.out.println("consumer4");
        System.out.println(demoService.getPermissions(1L));
        if (map.containsKey("username")&&map.containsKey("userpwd")) {
            String username=map.get("username");
            String password=map.get("userpwd");
            return "login successfully";

        }else {
            return "null verification";
        }
    }

}
