package com.runner.testworks.controller.shaoxin2yuan;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/shaoxin/sms")
public class SmsController {


    @PostMapping("/send")
    public JSONObject send(HttpServletRequest request, @RequestBody JSONObject body){
        Enumeration<String> headerNames = request.getHeaderNames();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code1","1");

        return jsonObject;


    }
}
