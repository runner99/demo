package com.runner99.sass.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.runner99.sass.domain.User;
import com.runner99.sass.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {


    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/get")
    public String get(){
        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        return "asdf";
    }
}
