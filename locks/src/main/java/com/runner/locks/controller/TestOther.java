package com.runner.locks.controller;

import com.runner.locks.service.Testservices01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestOther {

    @Autowired
    private Testservices01 testservices01;

    @RequestMapping("/testother")
    public void testother(){
        testservices01.test01();
    }
}
