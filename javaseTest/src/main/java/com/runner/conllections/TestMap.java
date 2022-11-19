package com.runner.conllections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

public class TestMap {
    @Test
    public void testdmeo01(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a","a1");
        map.put("b","b1");
        map.put("c","c1");
        map.put("d","d1");
        Set<String> strings = map.keySet();
        for (String key:strings){
            System.out.println(key+map.get(key));
        }
    }
}
