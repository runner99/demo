package com.runner.queueAndMap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/10/16 16:20
 */
public class JucMapTest {
    @Test
    public void test01(){
//        Map<String, String> map = new ConcurrentHashMap<>();
//        map.computeIfPresent()
        HashMap<String, List<String>> map = new HashMap<>();
        for (int i=0;i<10;i++){
//            如果不存在 i这个key则创建
            map.computeIfAbsent(i+"",k->new ArrayList<String>()).add("list"+i);
            map.computeIfAbsent(i+"",k->new ArrayList<String>()).add("list"+i);
        }
        System.out.println(map);

//        map.computeIfPresent()
    }
}
