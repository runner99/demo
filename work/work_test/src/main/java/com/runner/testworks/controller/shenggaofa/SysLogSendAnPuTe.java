package com.runner.testworks.controller.shenggaofa;

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

                        Thread.sleep(1000L);
//                        break;
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
//        String msg = "Antibot: [1]: {\"notify_time\":\"2025-04-11 09:42:14\",\"notify_type\":\"ALERT\",\"alert_time\":\"2023-10-24 12:30:00\",\"risk_name\":\"磁盘使用率超限\",\"channel\":\"default\",\"level\":\"midw\",\"content\":\"[11,0,33,2]\",\"data\":\"{\\\"IP\\\":\\\"172.31.34.201\\\"}]\",\"exp\":\"600\"}";
//        String msg="2024-11-20T12:42:14+08:00 orbstack Antibot: [1]: {\"notify_time\":\"2025-04-18 14:42:14\",\"notify_type\":\"RISK\",\"last_time\":\"2024-11-20 11:03:27\",\"first_time\":\"2024-11-14 12:42:22\",\"risk_name\":\"IP链路非法\",\"risk_level\":\"高风险\",\"subject_type\":\"IP\",\"risk_subject\":\"172.31.45.232\",\"risk_clue\":\"疑似伪造IP:127.0.0.1 位置在: X-Forwarded-For\",\"risk_description\":\"依次从 Cdn-Src-Ip,X-Forwarded-For,X-Real-Ip,Proxy-Client-Ip,Http-Client-Ip 获取IP，校验IP格式或链路合法性失败\",\"state\":\"待确认\",\"host\":\"10.0.72.42\",\"channel\":\"localhost\",\"attack_count\":\"9\"}";
        String msg="localdomain Antibot: [1]: ";


        HashMap<String, Object> iMap = new HashMap<>();
        iMap.put("e_category","alert");
        iMap.put("s_t_account","zazahuiaaaa");
        iMap.put("s_dev_ip","128.0.0.3");
        iMap.put("c_time", System.currentTimeMillis()+"");
        iMap.put("b_action", "GET");
        iMap.put("o_svr_ip", "129.3.3.3");
        iMap.put("o_svr_port", 122);
        iMap.put("o_statement", "/booking/library/user/11111");
        iMap.put("o_standard", "/booking/library/user/{parameter}");
        iMap.put("o_file_path", "/data/log/aaa.txt");
        iMap.put("r_req_matched_name", "ghghhhhhhh");
        iMap.put("r_resp_matched_name", "aaaaaaaaaaaa");
        iMap.put("r_risk_type", "asdffdsa");
        iMap.put("r_risk", 2);
        iMap.put("f_running_time", 9999);
        iMap.put("f_err", "jkl;;lkj");
        iMap.put("o_user_agent", "sercoollll");
        iMap.put("o_referrer", "referaaaaaaaaa");
        iMap.put("req_data_tag", "req");
        iMap.put("resp_data_tag", "resp");
        iMap.put("o_req_body", "{asdffdsa}");

        String jsonString = JSON.toJSONString(iMap);

        return msg+jsonString;
    }


}
