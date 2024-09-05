package com.runner.testworks.bjgw;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weizhenqiang
 * @date 2023/8/24 11:07
 */

@Slf4j
public class RiskRuleDescStringTest {
    @Test
    public void test01(){
        List<String> list = getStringList("23同一身份或客户端IP或终端IP，1分钟内发生100次登录99失败。66");
        System.out.println(list.get(0)+list.get(1)+list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(4));

    }


    public List<String> getStringList(String sentence){
        // 定义匹配数字的正则表达式
        String regex = "\\d+"; // 匹配一个或多个数字

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(sentence);

        // 存储拆分后的部分的字符串集合
        List<String> partsList = new ArrayList<>();

        int start = 0;

        // 查找并拆分句子
        while (matcher.find()) {
            int numberStart = matcher.start(); // 数字开始位置
            int numberEnd = matcher.end();     // 数字结束位置

            if (numberStart!=0) {
                partsList.add(sentence.substring(start, numberStart)); // 数字前的部分
            }
            partsList.add(sentence.substring(numberStart, numberEnd)); // 数字本身

            start = numberEnd; // 下一个部分的开始位置
        }

        // 将剩余部分存储到集合中
        if (start < sentence.length()) {
            partsList.add(sentence.substring(start)); // 剩余部分
        }

        // 输出拆分后的部分集合
        for (String part : partsList) {
            System.out.println("部分：" + part);
        }


        return partsList;
    }
}
