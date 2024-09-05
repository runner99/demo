package com.runner.testworks.controller.bjgw.bug;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weizhenqiang
 * @date 2023/12/20 16:17
 */
@Slf4j
public class Other {

    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>(1);

    private static AtomicLong count = new AtomicLong(0L);

    public static void main(String[] args) {

        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("test001");
            list.add("test002");
            list.add("test003");

            String join = String.join(",", list);
            System.out.println(join);
        } catch (Exception e) {

        }


    }
}
