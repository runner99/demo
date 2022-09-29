package com.runner.springbeantest.controller;

import com.runner.springbeantest.config.Serverconfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringTest {

    @RequestMapping("/test01")
    public String test01(){
        return Serverconfig.location+Serverconfig.getLOCAL();
    }
}
