package com.runner99.quartztest.config;

import com.runner99.quartztest.job.JobTest;
import com.runner99.quartztest.job.JobTest02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class QuartzConfiguration {

    @Autowired
    private JobTest jobTest;

    @Autowired
    private JobTest02 jobTest02;

    @Scheduled(cron="0/3 * * * * ? ")
    public void uploadFeedBackJob(){
        jobTest.execute ();
    }

    @Scheduled(cron="0/5 * * * * ? ")
    public void uploadFeedBackJob02() throws InterruptedException {
        jobTest02.execute ();
    }

}
