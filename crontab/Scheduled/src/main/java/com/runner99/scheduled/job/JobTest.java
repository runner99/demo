package com.runner99.scheduled.job;

import com.runner99.scheduled.service.JobTest01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class JobTest {
    @Autowired
    private JobTest01 jobTest01;

    public void execute(){
        jobTest01.test01();
    }
}
