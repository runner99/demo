package com.runner99.scheduled.service.impl;

import com.runner99.scheduled.service.JobTest01;
import org.springframework.stereotype.Service;

import java.lang.management.ThreadInfo;
import java.util.Date;

@Service
public class JobTest01Impl implements JobTest01 {
    @Override
    public void test01() {
        System.out.println("我是JobTest01");
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
