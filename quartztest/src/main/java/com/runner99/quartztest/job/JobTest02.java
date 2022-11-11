package com.runner99.quartztest.job;

import com.runner99.quartztest.service.ServiceTest02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobTest02 {

    @Autowired
    private ServiceTest02  serviceTest02;

    public void execute() throws InterruptedException {
        serviceTest02.jobTest02();
    }
}
