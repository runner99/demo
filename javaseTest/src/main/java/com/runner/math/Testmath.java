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
}
