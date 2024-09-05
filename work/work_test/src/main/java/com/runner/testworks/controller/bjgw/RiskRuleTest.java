package com.runner.testworks.controller.bjgw;

import com.runner.testworks.controller.bjgw.vo.RiskRuleEnum;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weizhenqiang
 * @date 2023/8/22 10:56
 */
public class RiskRuleTest {
    public static void main(String[] args) {
        String text = "同一身份或客户端IP或终端IP，1分钟内发生999次登录失败。";
//        String text = "同一身份或客户端IP或终端IP，一分钟内发生六次登录失败。";
//        matchParams(text);


        ArrayList<String> list = new ArrayList<>();
//        list.add("暴力破解");
//        list.add("撞库");
        list.add("test");
        list.stream().forEach(obj->{
            switch (obj){
                case "暴力破解":
                    System.out.println("暴力破解");
                    break;
                case "撞库":
                    System.out.println("撞库");
                    break;
            }
        });


    }

    public static void matchParams(String text){
        ArrayList<Integer> params = new ArrayList<>();
        String regex = "\\d+";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        while (matcher.find()) {
            String group = matcher.group();
            params.add(Integer.parseInt(group));
        }
        params.stream().forEach(System.out::println);
    }
}
