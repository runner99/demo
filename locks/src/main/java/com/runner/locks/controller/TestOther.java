package com.runner.locks.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.runner.locks.service.Testservices01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class TestOther {

    @Autowired
    private Testservices01 testservices01;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/testother")
    public void testother(){
        testservices01.test01();
    }

    @GetMapping("/test/{woya}")
    public void testwoya(@RequestParam("id")String id,
                         @PathVariable("woya")String woya){
        System.out.println(id);
        System.out.println(woya);
    }

    @GetMapping("/test/count")
    public String testcount(){
        int i =0;
        while (true){
            redisTemplate.opsForValue().set("woya"+i,i);
            System.out.println(redisTemplate.opsForValue().get("woya"+i));
            i++;

        }
    }
    @GetMapping("/test/del")
    public String testdel(){
        String begin = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        for (int i = 0 ;i<100000000;i++ ){
            System.out.println(redisTemplate.delete("woya"+i));
        }
        String end = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return begin+"::"+end;
    }
    @GetMapping("/test/pressure")
    public void testde2() throws InterruptedException {
        while (true){
            for (int i = 0;i<100;i++){
                String uuid = String.valueOf(UUID.randomUUID());
                redisTemplate.opsForValue().set("key"+uuid,i);
                redisTemplate.delete("keys"+uuid);
            }
            Thread.sleep(1000);
        }

    }
}
