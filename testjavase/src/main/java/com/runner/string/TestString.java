package com.runner.string;

import org.junit.Test;

public class TestString {
    @Test
    public void demo01(){
        String a= "jkl";
        String b= "jkl";
        String c= new String("jkl");
        System.out.println(a==b);
    }
}
