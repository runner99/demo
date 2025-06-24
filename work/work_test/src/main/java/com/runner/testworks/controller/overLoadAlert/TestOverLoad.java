package com.runner.testworks.controller.overLoadAlert;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestOverLoad {
    public static Cache<String, String> AlertGatewayCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .maximumSize(10_000)
            .build();


    private static volatile Boolean isSend=Boolean.TRUE;

    public static void main(String[] args) throws InterruptedException {

//        new Thread(() -> {
//            while (isSend) {
//                AlertGatewayCache.put("initten_gatewayparam", "aaaa" + System.currentTimeMillis());
//            }
//        }).start();
//
//        ConcurrentMap<@NonNull String, @NonNull String> map =
//                AlertGatewayCache.asMap();
//
//
//        List<String> collect = AlertGatewayCache.asMap().keySet().stream().filter(obj -> obj.startsWith("initten")).map(k -> {
//            return map.get(k);
//        }).collect(Collectors.toList());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dbAuditFlow",1600000);
        Long aLong = jsonObject.getLong("dbAuditFlow");
        String string = jsonObject.getString("dbAuditFlow");

        long l = jsonObject.getLong("dbAuditFlow") / 1000000;
        System.out.println(1);


    }
}
