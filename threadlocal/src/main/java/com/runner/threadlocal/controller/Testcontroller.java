package com.runner.threadlocal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @RequestMapping("/hello")
    public String hello(){
        return "你好啊😁";
    }
}
