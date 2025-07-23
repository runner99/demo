package com.runner.testworks.controller.gondan;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GongDanController {

    private static final ArrayList<Integer> WORK_STATUSS = new ArrayList<>(Arrays.asList(
            1,
            5
    ));

    private static final Long DELAY_TIME = 1000L * 60 * 60;

    public static void main(String[] args) {

        Cache<String, HashMap<String,String>> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(10)
                .build();


        String tenantId="initten";


        HashMap<String, String> map = new HashMap<>();
        map.put("111","222");
        cache.put(tenantId,map);


        String key="aaaa";
        String value="bbbb";


        HashMap<String, String> workDtoHashMap = cache.getIfPresent(tenantId);

        if (workDtoHashMap==null){
            workDtoHashMap = new HashMap<>();
            workDtoHashMap.put(key,value);
            cache.put(tenantId,workDtoHashMap);
        }else {
            workDtoHashMap.put(key,value);
        }


        HashMap<String, String> aaa = cache.getIfPresent(tenantId);


        System.out.println(1);
//
//        manualCache.put("aaa","bbbbbbbbb");
//        manualCache.put("bbb","bbbbbbbbb");
//        manualCache.put("ccc","bbbbbbbbb");
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        manualCache.put("ddd","bbbbbbbbb");
//
//        while (true){
//            System.out.println(manualCache.getIfPresent("aaa"));
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//


    }





}
