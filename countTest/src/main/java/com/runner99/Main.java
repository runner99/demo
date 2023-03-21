package com.runner99;

import org.junit.Test;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author weizhenqiang
 * @date ${DATE} ${TIME}
 */
public class Main {
    public static List<Integer> list = new ArrayList<>();

    @Test
    public void test01() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        while (true) {
            System.out.println(11);
            try {
                count();
            } catch (Exception e) {
                System.out.println("最外层异常");
            }

        }
    }

    public static void count() {
        try {
            list.forEach(sub -> {
                try {
                    if (sub.equals(0)) {
                        return;
                    } else {
                        System.out.println(sub);
                    }
                } catch (Exception e) {
                    System.out.println("最内层异常");
                }
            });
        } catch (Exception e) {
            System.out.println("我是内层异常");
        }
    }
}
