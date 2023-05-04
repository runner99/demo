package com.runner99.scheduled.service.impl;

import com.runner99.scheduled.service.JobTest02;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobTest02Impl implements JobTest02 {
    @Override
    public void test02() {

        System.out.println("我是JobTest02");
        System.out.println(Thread.currentThread().getName());
        System.out.println("开始阻塞10s");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(new Date());
    }
}
