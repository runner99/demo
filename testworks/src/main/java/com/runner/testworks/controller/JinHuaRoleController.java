package com.runner.testworks.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author weizhenqiang
 * @date 2023/6/8 14:21
 */

@RestController
public class JinHuaRoleController {


    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("method", "PUT");
        map.put("name", "haha");
        Optional<HashMap<String, Object>> map1 = Optional.ofNullable(map);


        String string = map1.map(sub -> sub.get("method")).orElse("GET").toString();
        HttpMethod post = HttpMethod.resolve(string);
//        HttpMethod method = Optional.ofNullable(post).orElse(HttpMethod.GET);
        System.out.println(post);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("data", "jkl");
//        JSONObject jkl = new JSONObject(map);
//        Boolean data = jkl.getBoolean("data");
//        if (data){
//
//        }



//        String method=null;
//        HttpMethod post = HttpMethod.resolve(method);
//        HttpMethod method1 = Optional.ofNullable(post).orElse(HttpMethod.GET);
//        if (post == null) {
//            post=HttpMethod.GET;
//        }
//        System.out.println(post.toString());

    }

}
