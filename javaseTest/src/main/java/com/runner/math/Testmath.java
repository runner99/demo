package com.runner.math;

import org.junit.Test;

public class Testmath {
    @Test
    public void demo01(){
        Integer a = 12344;
        Integer b = 12344;
        System.out.println(a==b);
    }

    @Test
    public void demo02(){
        double v = 2.12311;
        System.out.println(String.format("%.2f", v));
        System.out.println(Double.parseDouble(String.format("%.2f", v)));
    }

    @Test
    public void demo03(){
        Integer a=new Integer(12);
        int b=12;
        Integer c=12;
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(b==c);
        System.out.println(a.intValue()==b);
        System.out.println(a.intValue()==c);
    }


    @Test
    public void demo04(){
        int random = (int)(Math.random()*10);
//        System.out.println(random);

        String code = "";
        for (int i=0 ;i<5;i++){
            code+=(int) (Math.random() * 10);
        }
        System.out.println(code);
    }
}
