package com.runner.testworks.controller.openapi;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author weizhenqiang
 * @date 2024/8/28 14:17
 */
@RestController
@RequestMapping(value = "/openapi")
public class OpenController {

    @GetMapping("test")
    public Object test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",12341234);
        jsonObject.put("name","aaaaaaaaaaaaaaa");

//        ArrayList<String> list = new ArrayList<>();
//        list.add("asdf");
//        list.add("asdfaaa");
//        list.add("asdfaaabbb");
        return jsonObject;
    }
}
