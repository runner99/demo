package com.runner99.scheduled.job;

import com.runner99.scheduled.service.JobTest02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobTestNew {

    @Autowired
    private JobTest02 jobTest02;
    public void execute(){
        jobTest02.test02();
    }
}
