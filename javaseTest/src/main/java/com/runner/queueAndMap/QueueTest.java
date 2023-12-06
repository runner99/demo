package com.runner.queueAndMap;

import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author weizhenqiang
 * @date 2023/10/16 15:33
 */
public class QueueTest {


//    LinkedBlockingDeque
//    队列里面的元素不可为null
    @Test
    public void test01() throws Exception {
        LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>(5);

        int i = 0;
        while (i < 10) {
            System.out.println(queue.offer("a" + i));
            i++;
        }
//        queue.offer(null);//LinkedBlockingDeque不可放入null元素
        queue.offer("haha",5,TimeUnit.SECONDS);//如果空间满了则等待5秒
        while (true) {
            String poll = queue.poll();
            if (poll != null) {
                System.out.println(poll);
                continue;
            }
            break;
        }
        System.out.println(LocalTime.now());
        System.out.println(queue.poll(10,TimeUnit.SECONDS));//如果没数据则等待10秒
        System.out.println(LocalTime.now());
    }








}
