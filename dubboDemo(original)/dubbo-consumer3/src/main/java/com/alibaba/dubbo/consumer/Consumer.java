package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@Controller
public class Consumer {
    @Autowired
    private DemoService demoService;

    @RequestMapping(value = { "/test/register" }, method = RequestMethod.POST)
    @ResponseBody
    public String register(String jsonarray){
        System.out.println("consumer4 start");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer4 start");
        demoService = context.getBean(DemoService.class);
        System.out.println("consumer4");
        System.out.println(demoService.getPermissions(1L));
        com.alibaba.fastjson.JSONObject jsonObject=(com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(jsonarray);
        User user=new User(Integer.parseInt(jsonObject.getString("id")),
                jsonObject.getString("nickname"),
                jsonObject.getString("password"),
                jsonObject.getString("firstname"),
                jsonObject.getString("lastname"));



        return "ok";
    }
}
