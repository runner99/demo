package com.runner.provider.controller;

import com.runner.provider.common.Constant;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @RequestMapping("/teststate")
    public Boolean teststate(){
        return Constant.isOnline;
    }

    @RequestMapping("/test02")
    public String teststate02(HttpServletRequest request){
        String hello = request.getParameter("hello");

        return hello;
    }

}
