package com.runner.testworks.controller.gondan;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.lucene.util.RamUsageEstimator;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FilterSend {

    private static List<HashSet<Character>> hashSets = new CopyOnWriteArrayList<>();


    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);

    public static void main(String[] args) {


//        new Thread(() -> {
//            try {
//                while (true) {
//                    String s = "aaa" + System.currentTimeMillis();
//                    queue.put(s);
//
//                    System.out.println("send"+s);
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//
//        new Thread(() -> {
//            try {
//                while (true) {
//                    String take = queue.take();
//                    System.out.println(take);
//                    Thread.sleep(1000L);
//                }
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();


//        // 获取当前时间的毫秒戳
//        long now = System.currentTimeMillis();
//
//
//        // 计算当前整分的毫秒戳
//        long startOfMinute = now - now % (60 * 1000);
//
//        System.out.println("当前整分的毫秒戳: " + startOfMinute);




// 获取当天的开始时间
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        // 转换为毫秒戳（当日开始时间戳）
        long startOfDayTimestamp = startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 获取当天的结束时间（23:59:59.999）
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // 转换为毫秒戳（当日结束时间戳）
        long endOfDayTimestamp = endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        System.out.println("当日的开始时间戳: " + startOfDayTimestamp);
        System.out.println("当日的结束时间戳: " + endOfDayTimestamp);

    }


}
