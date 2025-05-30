package com.runner.testworks.controller.dongyang;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConfigIF;
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
//    private static final String HOST = "127.0.0.1";
    private static final String HOST = "192.168.52.204";

    private static final int PORT = 1469;


    /**
     * 一批消息数量
     */
    private static final int BATCH_SIZE = 1;

    private static SyslogIF syslog = null;


    public static void main(String[] args) {

        Integer core = 1;
        syslog = Syslog.getInstance(SyslogConstants.UDP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(Integer.MAX_VALUE);
        syslog.getConfig().setTruncateMessage(Boolean.FALSE);
        syslog.getConfig().setSendLocalName(Boolean.FALSE);




        // 自定义报文头信息


        SyslogConfigIF config = syslog.getConfig();
        config.setSendLocalTimestamp(Boolean.FALSE);
        config.setSendLocalName(true); // 发送本地主机名
        config.setLocalName("USM"); // 自定义本地主机名
        config.setFacility(SyslogConstants.FACILITY_USER); // 设置设施



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
                        syslog.log(SyslogConstants.LEVEL_ERROR, String.join("\n", messages));

                        /**
                         * 是否只发一次
                         */
                        Thread.sleep(3000L);
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
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        stringObjectHashMap.put("userName","hehehieh");
        stringObjectHashMap.put("name","asdffdsa");
        stringObjectHashMap.put("rankID","1j10d1k0dk114");
        stringObjectHashMap.put("logType","hehehieh");
        stringObjectHashMap.put("result","hehehieh");
        stringObjectHashMap.put("date","2025-05-09 14:15:00");
        stringObjectHashMap.put("operateType","select");
        stringObjectHashMap.put("sourceIP","127.0.0.1");
        stringObjectHashMap.put("operateContent","hehehieh");

        String msg="{\"userName\":\"mawei\",\"name\":\"\\u9a6c\\u4f1f\",\"rankID\":2,\"logType\":1,\"result\":1,\"date\":\"2025-05-12 17:23:59\",\"operateType\":\"\\u8fde\\u63a5\\u4e3b\\u673a\",\"sourceIP\":\"10.16.240.121\",\"operateContent\":\" root@172.16.99.79:22 success\"}";
//        return JSON.toJSONString(stringObjectHashMap);


//        return "明御运维审计与风险控制系统 syslog test";
        return msg;
    }


}
