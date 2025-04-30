package com.runner.testworks.controller.dongyang;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSendUdpBv2 {

    /**
     * dsc设备地址
     */
    private static final String HOST = "192.168.52.204";

    private static final int PORT = 1468;


    /**
     * 一批消息数量
     */
    private static final int BATCH_SIZE = 1;

    private static SyslogIF syslog = null;


    public static void main(String[] args) {

        Integer core = 1;
        syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(Integer.MAX_VALUE);
        syslog.getConfig().setTruncateMessage(Boolean.FALSE);
        syslog.getConfig().setSendLocalName(Boolean.FALSE);


        /**
         * 一个间隔发送的数据量
         */
        AtomicInteger count = new AtomicInteger(0);

        /**
         * 总共发送的数据量
         */
        AtomicLong allCount = new AtomicLong(0);

        ExecutorService executorService = Executors.newFixedThreadPool(core);

        for (int i = 0; i < core; i++) {
            executorService.execute(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        List<String> messages = new ArrayList<>();
                        for (int j = 0; j < BATCH_SIZE; j++) {
                            String msg = buildMessage();
                            messages.add(msg);
                            count.incrementAndGet();
                        }
                        syslog.log(SyslogConstants.LEVEL_INFO, String.join("\n", messages));

                        /**
                         * 是否只发一次
                         */
                        Thread.sleep(500L);
//                        break;
                    }
                } catch (Exception e) {
                    log.error("" + e);
                }
            });
        }

        Integer interval = 1;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            int i = count.get();
            count.getAndAdd(-i);

            allCount.getAndAdd(i);

            System.out.println("在过去" + interval + "秒内，发送消息数：" + i+",总共发送数据："+allCount.get());
        }, 1, interval, TimeUnit.SECONDS);

    }


    /**
     * 消息体
     *
     * @return
     */
    private static String buildMessage() {
        String msg="Tag {\"userName\": \"xxxx\",\"name\": \"xx\",\"rankID\": x,\"logType\": x,\"result\": x,\n" +
                "     * \"date\": \"yyyy-mm-dd hh:mm:ss\",\"operateType\": \"xxxxxx\",\n" +
                "     * \"sourceIP\": \"x.x.x.x\",\"operateContent\": \" root@10.50.82.196:22 success\"}";
        return JSON.toJSONString(msg);
    }


}
