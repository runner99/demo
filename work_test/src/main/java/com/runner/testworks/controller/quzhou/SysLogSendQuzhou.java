package com.runner.testworks.controller.quzhou;

import com.alibaba.fastjson.JSON;
import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
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

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSendQuzhou {

    /**
     * dsc设备地址
     */
//    private static final String HOST = "192.168.52.180";
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
            System.out.println("在过去" + interval + "秒内，发送消息数：" + i);
        }, 1, interval, TimeUnit.SECONDS);

    }


    /**
     * 消息体
     *
     * @return
     */
    private static String buildMessage() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("c_time", System.currentTimeMillis());
//        map.put("c_time",1704263441000L);
        map.put("e_category", "alert");
        map.put("e_type", "db_access");
//        map.put("e_type","db_logon");


//
        map.put("o_svr_ip", "192.168.52.188");
        map.put("o_svr_port", 13306);
        map.put("o_statement", "select * from test;");
        map.put("o_object", "analysis_sample");
        map.put("o_schema", "ddac");


        map.put("s_dev_ip", "192.12.12.12");
        map.put("s_dev_port", 666);
        map.put("r_risk", 0);
//        map.put("r_risk_type", "ggggggg");

        map.put("f_affected", 10000);
        map.put("b_action", "select");
        map.put("o_type", "rdstest");
//        map.put("r_response","阻断连接");


        map.put("s_db_user", "TEST666");

//        map.put("s_t_account","yewutest");
//        map.put("s_t_account","yunwei");


//        map.put("s_app_name","asdfasdf");
//        map.put("o_standard","select * from ASSERT_DS_BASE");
//        map.put("o_standard","select * from ASSERT_DS_BASE");
//        map.put("r_response","阻断连接");
//        map.put("r_risk_type","大量拖库");
        return JSON.toJSONString(map);
    }


}
