package com.runner.threadlocal.controller;

import com.runner.threadlocal.common.UserEntity;
import com.runner.threadlocal.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {


    @RequestMapping("/login")
    public String login(){
        return "你好啊😁";
    }

    @RequestMapping("/hello")
    public String hello(){
        UserEntity userEntity = ThreadLocalUtil.get();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userEntity.toString();
    }

    @RequestMapping("/hello02")
    public String hello02(){
        UserEntity userEntity = ThreadLocalUtil.get();
        return userEntity.toString();
    }
}
