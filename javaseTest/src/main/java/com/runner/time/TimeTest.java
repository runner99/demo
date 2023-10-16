package com.runner.time;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TimeTest {

    //日期转为规定字符串
    @Test
    public void test01(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(new Date());
        System.out.println(str);
        System.out.println(new Date());
    }

    //字符串转为日期
    @Test
    public void test02() throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse("20201101010100");
        System.out.println(date);
        System.out.println(new Date());
    }

    //时间戳转为日期字符串
    @Test
    public void test03() throws ParseException {
        String yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss").format(1668064236000L);

        System.out.println(yyyyMMddHHmmss);
    }

    //分别获取日期，
    @Test
    public void test04() {
        LocalDate now = LocalDate.now();
        System.out.println(now);//2023-10-16
        LocalTime now2 = LocalTime.now();
        System.out.println(now2.toString());//15:27:16.278
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);//2023-10-16T15:27:16.278
    }

}
