package com.runner.testworks.controller.henan;


import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.json.JSONObject;
import com.sun.net.httpserver.Headers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heNan")
public class HeNanTestController {

    @GetMapping("/openapi/aas/5.0/DescribeResApi.json")
    public JSONObject getReslut(@RequestParam(value = "data") String data) {
//        Headers headers = request.getHeaders();
        return null;
    }
}
