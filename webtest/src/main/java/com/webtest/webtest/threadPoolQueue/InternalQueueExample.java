package com.webtest.webtest.threadPoolQueue;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.webtest.webtest.threadPoolQueue.service.TestService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InternalQueueExample {

    private static Long SLEEP_TIME = 100L;
    public static void main(String[] args) {
        // 创建固定大小的线程池，使用内部队列作为工作队列
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        TestService service = new TestService();

        // 创建大量任务并提交给线程池执行
        for (int i = 0; i < 100; i++) {
            final int taskNumber = i;
            executorService.execute(() -> {
                service.sleep(SLEEP_TIME);
            });
        }

        // 关闭线程池
        executorService.shutdown();
    }
}



