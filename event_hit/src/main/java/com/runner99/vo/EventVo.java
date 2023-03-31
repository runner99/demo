package com.runner99.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/3/27 17:50
 */
@Data
public class EventVo {
    private String prop1;
    private String prop2;
    private String prop3;
    private String prop4;
    private String prop5;
    private String prop6;
    private String prop7;
    private String prop8;
    private String prop9;
    private String prop10;
    private String prop11;
    private String prop12;
    private String prop13;
    private String prop14;
    private String prop15;
    private String prop16;
    private String prop17;
    private String prop18;
    private String prop19;
    private String prop20;

    public Map<String,String> getAttributeMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("prop1",this.prop1);
        map.put("prop2",this.prop2);
        map.put("prop3",this.prop3);
        map.put("prop4",this.prop4);
        map.put("prop5",this.prop5);
        map.put("prop6",this.prop6);
        map.put("prop7",this.prop7);
        map.put("prop8",this.prop8);
        map.put("prop9",this.prop9);
        map.put("prop10",this.prop10);
        map.put("prop11",this.prop11);
        map.put("prop12",this.prop12);
        map.put("prop13",this.prop13);
        map.put("prop14",this.prop14);
        map.put("prop15",this.prop15);
        map.put("prop16",this.prop16);
        map.put("prop17",this.prop17);
        map.put("prop18",this.prop18);
        map.put("prop19",this.prop19);
        map.put("prop20",this.prop20);

        return map;

    }


}
