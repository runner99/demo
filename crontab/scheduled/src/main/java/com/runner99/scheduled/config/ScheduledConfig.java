package com.runner99.scheduled.config;

import com.runner99.scheduled.job.JobTest;
import com.runner99.scheduled.job.JobTestNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduledConfig {

    @Autowired
    private JobTest jobTest;

    @Autowired
    private JobTestNew jobTestNew;

    @Async("myAsync")
    @Scheduled(cron = "0/3 * * * * ? ")
    public void test01() {
        jobTest.execute();
    }

//    每天0点执行一次
    @Async("myAsync")
    @Scheduled(cron = "0/7 * * * * ? ")
    public void test02() {
        jobTestNew.execute();
    }
}
