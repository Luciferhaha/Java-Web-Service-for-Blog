package com.eden.controller;

import com.eden.model.BlogEntity;
import com.eden.model.UserEntity;
import com.eden.repository.BlogRepository;
import com.eden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlogRepository blogRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getUsers(ModelMap modelMap) {
        // 查询user表中所有记录
        List<UserEntity> userList = userRepository.findAll();
//        System.out.println(userList.get(0));
        // 将所有记录传递给要返回的jsp页面，放在userList当中
        modelMap.addAttribute("userList", userList);

        // 返回pages目录下的admin/users.jsp页面
        return "admin/users";
    }
    // get请求，访问添加用户 页面
    @RequestMapping(value = "/admin/users/add", method = RequestMethod.GET)
    public String addUser() {
        // 转到 admin/addUser.jsp页面
        return "admin/addUser";
    }
    // post请求，处理添加用户请求，并重定向到用户管理页面
    @RequestMapping(value = "/admin/users/addP", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") UserEntity userEntity) {
        // 注意此处，post请求传递过来的是一个UserEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'user'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(userEntity);

        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/admin/users";
    }
    // 查看用户详情
// @PathVariable可以收集url中的变量，需匹配的变量用{}括起来
// 例如：访问 localhost:8080/admin/users/show/1 ，将匹配 id = 1
    @RequestMapping(value = "/admin/users/show/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "admin/userDetail";
    }
    // 更新用户信息 页面
    @RequestMapping(value = "/admin/users/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "admin/updateUser";
    }

    // 更新用户信息 操作
    @RequestMapping(value = "/admin/users/updateP", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("userP") UserEntity user) {

        // 更新用户信息
        userRepository.updateUser(user.getNickname(), user.getFirstName(),
                user.getLastName(), user.getPassword(), user.getId());
        userRepository.flush(); // 刷新缓冲区
        return "redirect:/admin/users";
    }
    // 删除用户
    @RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Integer userId) {

        // 删除id为userId的用户
        userRepository.delete(userId);
        // 立即刷新
        userRepository.flush();
        return "redirect:/admin/users";
    }


    @RequestMapping(value = { "/test/login" }, method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Map<String, String> map){
        if (map.containsKey("username")&&map.containsKey("userpwd")) {
            if ( map.get("username").equals("test" ) &&  map.get("userpwd").equals("123456")) {
                return "successful login";
            } else {
                return "error verification";
            }
        }else {
            return "null verification";
        }

    }
    @RequestMapping(value = { "/test/register" }, method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody UserEntity userEntity, PrintWriter out){
        out.println(userEntity.getNickname());
        List<UserEntity> userList = userRepository.findAll();
        userList.add(userEntity);
        out.println(userEntity.getId());
        out.println(userEntity.getFirstName());
        try {
            userRepository.saveAndFlush(userEntity);

        }catch (Exception error){
            error.printStackTrace();
        }


        return "ok";
    }

    @RequestMapping(value = { "/test/newtopic" }, method = RequestMethod.POST)
    @ResponseBody
    public String addTopic(@RequestBody String jsonstr) {
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("you are in now");
        com.alibaba.fastjson.JSONObject jsonObject=(com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(jsonstr);
        BlogEntity blogEntity=new BlogEntity();
        UserEntity userEntity = userRepository.findOne(Integer.parseInt(jsonObject.getString("user_id")));
        blogEntity.setUserByUserId(userEntity);
        blogEntity.setPubDate(sqlDate);
        blogEntity.setTitle(jsonObject.getString("title"));
        blogEntity.setContent(jsonObject.getString("content"));
        blogEntity.setPubDate(sqlDate);
        System.out.println("are you here?");

        try {
            blogRepository.saveAndFlush(blogEntity);

        }catch (Exception error){
            error.printStackTrace();
        }
        String jsonMessage = "{\"success\":\"true\",\"topic_id\":\"5b00f0808a45377c06ad6d6e\"}";

        com.alibaba.fastjson.JSONObject myJson = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(jsonMessage);
        return myJson.toString();


    }


}
