package com.runner.testworks.controller;

import com.alibaba.fastjson2.JSONObject;
import com.runner.testworks.pojo.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class TestZazahui {

    private static final ConcurrentMap<String, String> APP_CACHE = new ConcurrentHashMap<>(16);



    public static void main(String[] args) {


        ArrayList<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("111");
        strings.add("111");
        strings.add("111");

        List<String> collect = strings.stream().limit(-1).collect(Collectors.toList());
        System.out.println(1);

    }
}
