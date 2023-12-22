package com.runner.testworks.controller.bjgw.bug;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weizhenqiang
 * @date 2023/12/20 16:17
 */
@Slf4j
public class Other {

    private  static BlockingQueue<String> queue = new LinkedBlockingQueue<>(1);

    private static AtomicLong count = new AtomicLong(0L);

    public static void main(String[] args) {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            try {
                long l = count.get();
                count.getAndAdd(-l);
                log.info("count hhhhhhhhhh:{}",l);
                if (System.currentTimeMillis()%2==0){
                    System.exit(0);
                }
            }catch (Exception e){

            }
        },3,3,TimeUnit.SECONDS);

        while (true){
            log.info("ggggggg");
            count.incrementAndGet();
        }


    }
}
