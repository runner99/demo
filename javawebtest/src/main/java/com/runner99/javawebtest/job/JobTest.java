package com.runner99.javawebtest.job;

import com.runner99.javawebtest.service.ServiceTest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobTest {

    @Autowired
    private ServiceTest serviceTest;

//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        serviceTest.jobTest();
//    }
//    @Override
    public void execute(){
        serviceTest.jobTest();
    }
}
