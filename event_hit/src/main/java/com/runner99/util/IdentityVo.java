package com.runner99.util;

import java.util.HashMap;

/**
 * @author weizhenqiang
 * @date 2023/3/22 22:54
 */
public class IdentityVo {
    private HashMap<String,String> Identity_MAP = new HashMap<>();

    public IdentityVo(){
        int v = (int)(Math.random() * 9)+1;
        for (int i =1;i<10;i++){
            Identity_MAP.put("key"+i,"value"+i*v);
        }
    }
}
