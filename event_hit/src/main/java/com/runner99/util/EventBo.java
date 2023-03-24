package com.runner99.util;

import lombok.Data;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author weizhenqiang
 * @date 2023/3/22 14:24
 */
@Data
public class EventBo {
    
    private  HashMap<String,String> EVENT_MAP = new HashMap<>();

    public EventBo(){
        int v = (int)(Math.random() * 9)+1;
        for (int i =1;i<100;i++){
            EVENT_MAP.put("key"+i,"value"+i*v);
        }
    }


}
