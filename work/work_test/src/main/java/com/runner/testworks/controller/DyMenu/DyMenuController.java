package com.runner.testworks.controller.DyMenu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DyMenuController {
    public static void main(String[] args) {
        JSONArray objects = JSONUtil.parseArray(ResourcesFileUtil.readFileFromResources("DyMenuUrl.json"));

        objects.stream().forEach(obj->{
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.getStr("name"));
            System.out.println(jsonObject.getStr("url"));
        });

        System.out.println(1);
    }
}
