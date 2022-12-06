package com.runner99.mybatistest.controller;

import com.runner99.mybatistest.pojo.User;
import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class Test01 {

    @RequestMapping("/test01")
    public String test01(@RequestBody @Valid User user){
        System.out.println("----返回前-----");
        System.out.println(user.getDesc());
        return user.toString();
    }

    @RequestMapping("/test02")
    public String test02(@RequestParam("name") @NotNull String name){
        System.out.println("----返回前-----");
        System.out.println("hhhh");
        return name.toString();
    }

}
