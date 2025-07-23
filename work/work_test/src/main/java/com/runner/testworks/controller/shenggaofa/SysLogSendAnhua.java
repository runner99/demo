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
public class SysLogSendAnhua {

    private static final String HOST = "192.168.52.204";
    private static final int PORT = 1468;

    private static final int BATCH_SIZE = 10;

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
            System.out.println("在过去" + interval + "秒内，发送消息数：" + i);
        }, 1, interval, TimeUnit.SECONDS);

    }

    private static String buildMessage() {
        // 你的消息构建逻辑
        String msg = "影响对象:mysql.A.TEST01; " +
                "#012SQL标识:12816206170203243825 " +
                "#012应答错误号:0 " +
                "#012应答错误信息: " +
                "#012影响行数:1 " +
                "#012响应时间:46   结果集: " +
                "#012执行结果:成功 " +
                "#012风险类型:违反操作规则 " +
                "#012风险级别:高 " +
                "#012规则名称:select规则 " +
                "#012应用客户端IP: " +
                "#012应用客户端端口:0" +
                "#012应用用户名: " +
                "#012应用会话标识: " +
                "#012被保护数据库:测试mysql " +
                "#012流水标识:2211011316420011058 " +
                "#012捕获时间:2025-7-18 10:40:46 " +
                "#012SQL:/* ApplicationName=DBeaver 22.2.3 - SQLEditor <Script-34.sql> */SELECT DATABASE()   #012操作类型:SELECT " +
                "#012语句长度:86 " +
                "#012会话标识:4049961760031000000" +
                "#012会话开始时间:2025-7-18 10:40:46 " +
                "#012客户端IP:10.10.24.30 " +
                "#012客户端端口:58359 " +
                "#012客户端MAC:30B0377A7D89 " +
                "#012数据库用户:ROOT " +
                "#012客户端工具:MySQL Connector/J " +
                "#012客户端主机名称:nwonknu " +
                "#012操作系统用户:nwonknu " +
                "#012数据库IP:192.168.52.201 " +
                "#012数据库端口:13306 " +
                "#012数据库MAC:000C29108DB5 " +
                "#012数据库名称:SYS " +
                "#012登录信息:aaa " +
                "#012服务(实例)名:mysql " +
                "#012被保护数据库类型:MySQL";
        return msg;
    }


}
