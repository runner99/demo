package com.runner.testworks.controller.shenggaofa;

import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSendAnPuTe {

    private static final String HOST = "192.168.52.204";
    private static final int PORT = 1468;

    private static final int BATCH_SIZE=1;

    private static SyslogIF syslog = null;


    public static void main(String[] args) {

        Integer core = 1;
        syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);
        syslog.getConfig().setTruncateMessage(Boolean.FALSE);
        syslog.getConfig().setSendLocalName(Boolean.FALSE);


        AtomicInteger count = new AtomicInteger(0);

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

//                        Thread.sleep(500L);
                        break;
                    }
                } catch (Exception e) {
                    log.error("" + e);
                }
            });
        }

        Integer interval=1;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            int i = count.get();
            count.getAndAdd(-i);
            System.out.println("在过去"+interval+"秒内，发送消息数："+i);
        },1,interval, TimeUnit.SECONDS);

    }

    private static String buildMessage() {
        // 你的消息构建逻辑
        String msg = "{\"notify_time\":\"2025-03-19 17:42:14\",\"notify_type\":\"ALERT\",\"alert_time\":\"2023-10-24 12:30:00\",\"risk_name\":\"磁盘使用率超限\",\"channel\":\"default\",\"level\":\"midw\",\"content\":\"[11,0,33,2]\",\"data\":\"{\\\"IP\\\":\\\"172.31.34.201\\\"}]\",\"exp\":\"600\"}";

        return msg;
    }


}
