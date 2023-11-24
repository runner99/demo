package com.runner99.serverDemo.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.concurrent.*;

/**
 * @author weizhenqiang
 * @date 2023/11/13 19:09
 */
@Slf4j
@RestController
@RequestMapping("/thread")
public class ThreadController {
    public static LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>();

    public static void main(String[] args) {

        Executors.newFixedThreadPool(1).execute(() -> {
            product();
        });

        int core = 5;
        int max = 10;
//System.currentTimeMillis()
        ThreadPoolExecutor service = new ThreadPoolExecutor(core, max
                , 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("testhhh-thread-%d").build());


        for (int i = 0; i < core + 1; i++) {
            service.execute(() -> {
                while (true) {
                    String poll = queue.poll();
                    if (poll != null) {
                        log.info(poll);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }


    }

    public static void product() {
        while (true) {
            queue.offer(LocalTime.now().toString());
        }
    }
}
