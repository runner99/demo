package com.webtest.webtest.threadPoolQueue;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ExternalQueueExample {
    private static Long SLEEP_TIME = 100L;
    public static void main(String[] args) {
        // 创建外部阻塞队列
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(100);

        System.out.println(taskQueue.size());

        // 创建固定大小的线程池，并指定外部阻塞队列作为工作队列
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 创建大量任务并提交给线程池执行
        for (int i = 0; i < 100; i++) {
            final int taskNumber = i;
            executorService.execute(() -> {
                System.out.println("Task " + taskNumber + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Duration duration = Duration.ofDays(1L);
        // 关闭线程池
        executorService.shutdown();
    }
}
