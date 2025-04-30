package com.runner.testworks.controller.shaoxin;

import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import com.alibaba.fastjson2.JSONObject;


@Slf4j
public class SysLogSendAnHeng {

    /**
     * dsc设备地址
     */
//    private static final String HOST = "192.168.52.180";
    private static final String HOST = "192.168.52.191";

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
//                        Thread.sleep(500L);
                        break;
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

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("DEVICE_TYPE","数据库审计");
//        jsonObject.put("DST_IP","192.168.230.206");
//        jsonObject.put("DST_PORT",3306);
//        jsonObject.put("EVENT_CITY","杭州市");
//        jsonObject.put("EVENT_DEAL_ID","30626211774332928019"+System.currentTimeMillis());
//        jsonObject.put("EVENT_DEAL_MARK","lx测试描述");
//        jsonObject.put("EVENT_DEAL_OPER","admin");
//        jsonObject.put("EVENT_DEAL_TIME", Instant.now().getEpochSecond());
//        jsonObject.put("EVENT_DETAIL","【2023-10-11 17:20:59】【ASSET】【192.168.230.206】【56358】访问【192.168.230.206】【3306】【规则描述】【杭州市】【市本级】");
//        jsonObject.put("EVENT_ID","杭州市");
//        jsonObject.put("EVENT_LEVEL",2);
//        jsonObject.put("EVENT_NAME","批量拖库1697016059");
//        jsonObject.put("EVENT_REGION","市本级");
//        jsonObject.put("EVENT_TIME",Instant.now().getEpochSecond());
//        jsonObject.put("EVENT_TYPE","帐号安全");
//        jsonObject.put("EVENT_WORKFLOW",2);
//        jsonObject.put("SRC_IP","192.168.230.206");
//        jsonObject.put("SRC_PORT",44);
//        jsonObject.put("USERNAME","ASSET");

// 创建空 JSONObject
        JSONObject json = new JSONObject();

        // 逐条添加键值对
        json.put("EVENT_DEAL_TIME", Instant.now().getEpochSecond()+"");
        json.put("EVENT_DEAL_ID", "TB-20230727-0002");
        json.put("EVENT_WORKFLOW", "已处置");
        json.put("EVENT_DEAL_OPER", "上虞区大数据发展管理中心");
        json.put("EVENT_DEAL_MARK", "");  // 空字符串保留
        json.put("EVENT_ID", "2023070516540"+System.currentTimeMillis());
        json.put("EVENT_TIME", Instant.now().getEpochSecond()+"");
        json.put("EVENT_TYPE", "SQL数据泄漏事件");
        json.put("EVENT_CITY", "绍兴市");
        json.put("EVENT_REGION", "上虞区");
        json.put("SRC_IP", "10.23.205.67");
        json.put("SRC_PORT", "23226");
        json.put("DST_IP", "10.23.195.99");
        json.put("DST_PORT", "23317");
        json.put("USERNAME", "上虞区大数据发展管理中心");
        json.put("EVENT_LEVEL", "");      // 空字符串保留
        json.put("DEVICE_TYPE", "SQL数据泄漏事件");
        json.put("EVENT_NAME", "test11111111111116666666666666");
        json.put("EVENT_DETAIL", "");     // 空字符串保留


        return json.toJSONString();
//        return jsonObject.toString();
    }


}
