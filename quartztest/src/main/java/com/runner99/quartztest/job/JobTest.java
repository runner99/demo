package com.runner99.quartztest.job;

import com.runner99.quartztest.service.ServiceTest;
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
