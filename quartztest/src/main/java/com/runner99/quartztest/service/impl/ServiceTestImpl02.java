package com.runner99.quartztest.service.impl;

import com.runner99.quartztest.service.ServiceTest;
import com.runner99.quartztest.service.ServiceTest02;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ServiceTestImpl02 implements ServiceTest02 {
    @Override
    public void jobTest02() {
        Date date = new Date();
        String yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        System.out.println("现在是:"+yyyyMMddHHmmss);
        System.out.println("这是一个Test02小测试");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
