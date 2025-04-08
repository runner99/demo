package com.runner.testworks.controller.bjgw;

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
public class SysLogSendbug {

    private static final String HOST = "192.168.52.186";
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

                        Thread.sleep(500L);
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
        String msg = "|BSEC_DAS_DBSEC_CEF:|捕获时间:|:|:::" + TimeUtils.getStrByTime(System.currentTimeMillis(), TimeFormatEnum.formatter) + " |数据库IP:10.1.182.216 |数据库端口 :11521 |服务(实列)名 :scglib |数据库名称:N/A |客户端IP:10.1.182.136 |客户端端口:56098 |客户端MAC:C45444616D2E |客户端工具:JDBC THIN CLIENT |操作系统用户:richmail |应用用户名:N/A |数据库用户:CLOUDP |风险类型:gggggggg |风险级别:高 |规则名称:开打开打 |操作类型:SELECT |受影响对象:SGCCDB.CLOUDP.CLOUDP FILE_9 |响应时间:1182 |执行结果:成功" + " |影响行数:8888888 |SQL语句:select * from user;"
                + "|客户端操作系统用户:userTEST1"
                + "|主机名:GGGG"
                + "|应用用户:AAAA"
                + "|服务器IP:1.1.1.1"
                + "|服务器端口:2"
//                + "|访问对象:OBJECT"
                + "|数据库名:USER1"
                + System.nanoTime();
        return msg;
    }


}
