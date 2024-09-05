package com.runner.testworks.controller.qingli;

import cn.hutool.json.JSONObject;

import java.util.HashMap;

/**
 * @author weizhenqiang
 * @date 2023/10/11 19:59
 */
public class OtherTestDemo {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("jkl","jkl");

        JSONObject jsonObject = new JSONObject(map);
        boolean b = jsonObject.containsKey("jkl");
        System.out.println(b);

    }
}
