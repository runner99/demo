package com.runner.string;

import org.junit.Test;

import java.util.Arrays;

public class TestString {
    @Test
    public void demo01(){
        String a= "jkl";
        String b= "jkl";
        String c= new String("jkl");
        System.out.println(a==c);
    }

    @Test
    public void demo02(){
        String filename = "jklj.jpg";
        String filetype = filename.substring(filename.lastIndexOf("."));
        System.out.println(filetype);
        if(!filetype.equals(".png") && !filetype.equals(".PNG") && !filetype.equals(".JPG") && !filetype.equals(".jpg") && !filetype.equals(".JPEG") && !filetype.equals(".jpeg")){
            System.out.println("格式有误");
        }
        if(!(filetype.equals(".png") || filetype.equals(".PNG") || filetype.equals(".JPG") || filetype.equals(".jpg") || filetype.equals(".JPEG") || filetype.equals(".jpeg"))){
            System.out.println("格式有误");
        }
    }

    @Test
    public void demo03(){
        String message = "1+2";
        int i = message.lastIndexOf("+");
        String begin = message.substring(0,i);
        String end = message.substring(i+1);
    }

    @Test
    public void test04(){
        String accounts = "wei,xianghong,xiaobai";
        String[] split = accounts.split(",");
        String s = split[0];
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println(split[2]);
    }


    @Test
    public void test05(){
        String a =new String("aaa");
        String b = "aaa";
        char[] c= {'a','a','a'};
        System.out.println(a==b);
    }

    @Test
    public void test06(){
        String a="";
        System.out.println(a.equals(null));
    }

}
