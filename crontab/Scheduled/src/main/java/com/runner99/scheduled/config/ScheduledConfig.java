package com.runner99.scheduled.config;

import com.runner99.scheduled.job.JobTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledConfig {

    @Autowired
    private JobTest jobTest;

    @Scheduled(cron="0/3 * * * * ? ")
    public void test01(){
        jobTest.execute ();
    }
}
