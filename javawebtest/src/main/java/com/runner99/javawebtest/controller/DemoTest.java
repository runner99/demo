package com.runner99.javawebtest.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.runner99.javawebtest.utils.IpUtils;
import jodd.util.StringUtil;
import org.springframework.beans.BeanUtils;
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

    @RequestMapping(value = "demo02",method = RequestMethod.GET)
    public String test02(){
//        StringUtil.isEmpty()
        return null;
    }

}
