package com.webtest.webtest.threadPoolQueue.service;

/**
 * @author weizhenqiang
 * @date 2023/5/16 11:22
 */
public class TestService {
    public void sleep(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
