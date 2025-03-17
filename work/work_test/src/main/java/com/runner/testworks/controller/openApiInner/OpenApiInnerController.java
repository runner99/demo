package com.runner.testworks.controller.openApiInner;


import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "openApiInnerTest")
public class OpenApiInnerController {


    @GetMapping(value = "/get")
    public JSONObject get01(HttpServletRequest request){
        String header = request.getHeader("X-TENANT-ID");
        System.out.println("tenant id :"+header);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1);
        return jsonObject;
    }

    @GetMapping(value = "/getP")
    public JSONObject get02(@RequestParam("id") String id){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        return jsonObject;
    }

    @PostMapping(value = "/post")
    public JSONObject post01(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1);
        return jsonObject;
    }

    @PostMapping(value = "/postP")
    public JSONObject post02(@RequestBody JSONObject jsonObject){
        return jsonObject;
    }


}
