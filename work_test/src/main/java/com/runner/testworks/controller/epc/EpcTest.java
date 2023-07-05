package com.runner.testworks.controller.epc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.print.attribute.standard.Media;

/**
 * @author weizhenqiang
 * @date 2023/6/26 10:43
 */
public class EpcTest {
    public static void main(String[] args) {
        String msg="dev=\"jkl\" dname=\"测试\"";

        msg="{"+msg+"}";
        System.out.println(msg);
        msg=msg.replaceAll("=",":");
        System.out.println(msg);
        msg=msg.replaceAll(" ",",");
        System.out.println(msg);

        JSONObject jsonObject = JSONObject.parseObject(msg);
        System.out.println(jsonObject);
    }
}

