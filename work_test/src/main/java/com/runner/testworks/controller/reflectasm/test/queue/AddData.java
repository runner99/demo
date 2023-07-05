package com.runner.testworks.controller.reflectasm.test.queue;

import com.runner.testworks.controller.reflectasm.pojo.ReflectasmEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weizhenqiang
 * @date 2023/7/5 10:28
 */

@Slf4j
public class AddData implements Runnable{
    @Override
    public void run() {
        while (true){
//            DataQueue.EVENT_QUEUE.offer(new ReflectasmEntity(System.nanoTime(),"name"+System.nanoTime()));
            DataQueue.EVENT_QUEUE.offer(new ReflectasmEntity(System.nanoTime(),"name"+System.nanoTime(),"name01"+System.nanoTime(),"name02"+System.nanoTime(),"name03"+System.nanoTime(),"name04"+System.nanoTime(),"name05"+System.nanoTime(),"name06"+System.nanoTime(),"name07"+System.nanoTime(),"name08"+System.nanoTime(),"name09"+System.nanoTime(),"name10"+System.nanoTime()));
        }

    }
}
