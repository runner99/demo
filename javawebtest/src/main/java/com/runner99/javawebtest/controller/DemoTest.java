package com.runner99.javawebtest.controller;

import com.runner99.javawebtest.utils.IpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoTest {

    @RequestMapping(value = "demo01",method = RequestMethod.GET)
    public String test01(HttpServletRequest request){
        String ipAddr = IpUtils.getIpAddr(request);

        return ipAddr;
    }

}
