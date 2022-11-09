package com.runner.time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
