package com.runner99.util;

import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/4/3 17:26
 */
public class EventUtil {

    public static List<Map<String, String>> getEvents(int number) {
        List<Map<String, String>> list = new ArrayList<>();
        Set<Map<String, String>> ev = getEv(number);
        while (true){

            if (ev.size()==number){
                break;
            }else {
                Set<Map<String, String>> ev1 = getEv(number - ev.size());
                ev.addAll(ev1);
            }
        }
        ev.stream().forEach(event->list.add(event));

        return list;
    }

    public static Set<Map<String,String>> getEv(int number){
        HashSet<Map<String, String>> maps = new HashSet<>();
        for (int i = 0; i < number; i++) {
            HashMap<String, String> map = new HashMap<>();
            Random random = new Random();
            map.put("prop1", "prop1:" + random.nextInt(100));
            map.put("prop2", "prop2:" + random.nextInt(100));
            map.put("prop3", "prop3:" + random.nextInt(100));
            map.put("prop4", "prop4:" + random.nextInt(100));
            map.put("prop5", "prop5:" + random.nextInt(100));
            map.put("prop6", "prop6:" + random.nextInt(100));
            map.put("prop7", "prop7:" + random.nextInt(100));
            map.put("prop8", "prop8:" + random.nextInt(100));
            map.put("prop9", "prop9:" + random.nextInt(100));
            map.put("prop10", "prop10:" + random.nextInt(100));
            map.put("prop11", "prop11:" + random.nextInt(100));
            map.put("prop12", "prop12:" + random.nextInt(100));
            map.put("prop13", "prop13:" + random.nextInt(100));
            map.put("prop14", "prop14:" + random.nextInt(100));
            map.put("prop15", "prop15:" + random.nextInt(100));
            map.put("prop16", "prop16:" + random.nextInt(100));
            map.put("prop17", "prop17:" + random.nextInt(100));
            map.put("prop18", "prop18:" + random.nextInt(100));
            map.put("prop19", "prop19:" + random.nextInt(100));
            map.put("prop20", "prop20:" + random.nextInt(100));
            maps.add(map);
        }
        return maps;
    }
}
