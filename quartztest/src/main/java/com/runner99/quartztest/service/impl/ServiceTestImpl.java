package com.runner99.quartztest.service.impl;

import com.runner99.quartztest.service.ServiceTest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ServiceTestImpl implements ServiceTest {
    @Override
    public void jobTest() {
        Date date = new Date();
        String yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        System.out.println("现在是:"+yyyyMMddHHmmss);
        System.out.println("这是一个Test01小测试");
    }
}
