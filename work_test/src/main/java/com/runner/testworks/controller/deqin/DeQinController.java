package com.runner.testworks.controller.deqin;

import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weizhenqiang
 * @date 2023/12/27 14:29
 */
@RestController
@RequestMapping("/deqin")
public class DeQinController {
    public static void main(String[] args) {
//        Long aLong = new Long(1);
//        System.out.println(aLong);
        ConcurrentHashMap<Long, ConcurrentHashMap<String, AtomicLong>> cacheMap = new ConcurrentHashMap<>();

        long curentHour = ((System.currentTimeMillis() / TimeUtils.HOUR_TIME_STAMP) + 8) % 24;
        System.out.println(curentHour);
        cacheMap.keySet().stream().forEach(obj -> {
            if (!obj.equals(curentHour)) {
                cacheMap.remove(obj);
            }
        });

    }
}
