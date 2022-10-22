package com.runner.provider.controller;

import com.runner.provider.common.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/teststate")
    public Boolean teststate(){
        return Constant.isOnline;
    }

}
