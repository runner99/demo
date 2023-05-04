package com.runner.locks.jinhua.service;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.runner.locks.config.Result;
import com.runner.locks.jinhua.pojo.Person;
import com.runner.locks.jinhua.pojo.User;
import com.runner.locks.jinhua.util.HttpClientUtils;
import org.apache.catalina.connector.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/4/24 16:44
 */
@Service
public class HttpService {

    public static Object getObject(String url, Object o) throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.get(url));
        Object data = jsonObject.get("data");

        if (data == null) {
            return null;
        }
        return JSONObject.parseObject(data.toString(), o.getClass());


    }


    public static Map<String, Object> getParameters(String url) {
        Map<String, Object> map = new HashMap<>();
        String substring = url.substring(url.indexOf("?") + 1);
        String[] split = substring.split("&");
        for (int i = 0; i < split.length; i++) {
            String key = split[i].substring(0, split[i].indexOf("="));
            String value = split[i].substring(split[i].indexOf("=") + 1);
            map.put(key, value);

        }
        return map;
    }

    public static void main(String[] args) throws Exception {

//        String url="http://127.0.0.1:10001/test01?test01=100001&test02=weizhenqiang";
//        Object object = getObject(url, new User());
//
//
//        String url2="http://127.0.0.1:10001/test02?id=666&name=weizhenqiang&gender=boy";
//        Object object1 = getObject(url2, new Person());

//        String url = "http://localhost:10001/test03";
//        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.get(url));
//        JSONArray data =(JSONArray) jsonObject.get("data");
//
//        System.out.println("hhh");

    }
}
