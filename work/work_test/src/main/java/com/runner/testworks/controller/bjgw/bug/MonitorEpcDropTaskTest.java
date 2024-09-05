package com.runner.testworks.controller.bjgw.bug;

/**
 * @author weizhenqiang
 * @date 2023/12/19 19:49
 */
public class MonitorEpcDropTaskTest {

    public static void main(String[] args) {
        while (true){
            MonitorEpcDropTask.count.incrementAndGet();
        }
    }
}
