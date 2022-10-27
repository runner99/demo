package com.runner.locks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/testlock01")
    public String testlock01() throws InterruptedException {

        try {
            if (redisTemplate.opsForValue().setIfAbsent("key", "uuid", 100, TimeUnit.SECONDS)){
                System.out.println("业务处理");
    //            Thread.sleep(1000*99);
                return "加锁成功";
            }

        }catch (Exception e){
            return "加锁失败"+e;
        }
        return null;
    }

    @RequestMapping("/testlock02")
    public String testlock02(){

        System.out.println("666");

        return null;
    }

}
