package com.runner.testworks.controller.deviceClient;

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
public class SysLogSend {

    /**
     * dsc设备地址
     */
//    private static final String HOST = "192.168.52.180";
    private static final String HOST = "192.168.52.204";
//    private static final String HOST = "192.168.52.191";

    private static final int PORT = 1468;


    /**
     * 一批消息数量
     */
    private static final int BATCH_SIZE = 10;

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
                        Thread.sleep(1000L);
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

    private static Integer assetPort=1;
    private static String buildMessage() {

        assetPort++;

        System.out.println("port:"+assetPort);

        HashMap<String, Object> map = new HashMap<>();
        map.put("c_time", System.currentTimeMillis());
//        map.put("c_time",1747623600000L);
//        map.put("e_category", "alert");
        map.put("e_category", "common");
        map.put("e_type", "db_access");
//        map.put("e_type","db_logon");


//  192.168.52.201:13306
        map.put("o_svr_ip", "192.168.202.2");
        map.put("o_svr_port", 11);
        map.put("o_statement", "select * from Aassert_account;");
        map.put("o_object", "ASSERT_ACCOUNT");
        map.put("o_schema", "initten_soc");


        map.put("s_dev_ip", "192.168.52.100");
        map.put("s_dev_port", 111);
        map.put("r_risk", 3);
        map.put("r_risk_type", "ggggggg");

        map.put("f_affected", 999999);
        map.put("b_action", "SELECT");
        map.put("o_type", "mysql");
        map.put("r_response","行为风险");


        map.put("s_db_user", "渣渣辉");

        map.put("s_t_account","yewutest");
//        map.put("s_t_account","yunwei");


        map.put("s_app_name","nameaaaaaa");
        map.put("o_standard","SELECT * from assert_account");
        map.put("r_matched_name","asdffdsa1");
        map.put("f_err","0");
        map.put("f_running_time",100000);
        return JSON.toJSONString(map);
    }


}
