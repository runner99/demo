package com.runner.locks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class LocksApplicationTests {


    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {

        Set<String> hot01 = redisTemplate.keys("wei*");
        Set<String> hot02 = scan("wei*");

//        for (int i = 1; i < 100001; i++) {
//            redisTemplate.opsForValue().set("hzmc" + i, "hzmcvalue" + i);
//        }
//        for (int i=1;i<10001;i++){
//            redisTemplate.opsForValue().set("wei"+i,"zhenqiang"+i);
//        }

        long keysbegin = System.currentTimeMillis();
        Set<String> keys = redisTemplate.keys("wei*");
        long keysend = System.currentTimeMillis();
        System.out.println("keys耗时"+(keysend - keysbegin)+"毫秒");

        long scanbegin = System.currentTimeMillis();
        Set<String> scan = scan("22222*");
        long scanend = System.currentTimeMillis();
        System.out.println(scan.size()==0);
        System.out.println("scan耗时"+(scanend - scanbegin)+"毫秒");

    }

    public Set<String> scan(String pattern) {
        Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern).count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
        return keys;
    }

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        System.out.println(set.size()==0);
        System.out.println(set==null);
    }



}
