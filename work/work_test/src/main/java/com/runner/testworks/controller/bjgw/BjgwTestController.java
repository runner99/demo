package com.runner.testworks.controller.bjgw;

import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weizhenqiang
 * @date 2023/8/4 17:50
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class BjgwTestController {

    private static final String HOST = "192.168.52.201";
    private static final int PORT = 1468;

    private static boolean flag=true;
    @GetMapping("/oom")
    public void oom(){
        log.info("开始发送消息");
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        flag=true;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (flag) {
                        //        安华金和审计
                        String msg = "BSEC DAS DBSEC CEF:|捕获时间:" + TimeUtils.getStrByTime(System.currentTimeMillis(), TimeFormatEnum.formatter) + " |数据库IP:10.1.182.216 |数据库端口 :11521 |服务(实列)名 :scglib |数据库名称:N/A |客户端IP:10.1.182.136 |客户端端口:56098 |客户端MAC:C45444616D2E |客户端工具:JDBC THIN CLIENT |操作系统用户:richmail |应用用户名:N/A |数据库用户:CLOUDP |风险类型:N/A |风险级别:N/A |规则名称:开打开打 |操作类型:SELECT |受影响对象:SGCCDB.CLOUDP.CLOUDP FILE_9 |响应时间:1182 |执行结果:" + (System.nanoTime() % 2 == 0 ? "失败" : "成功") + " |影响行数:88 |SQL:SELECT APP_FILE_ID，OFS_FILE_ID，FILE_NAME，FILE_LEVEL，FILE_COUNT，OISK_TYPE，FILE_TYPE,FILE SORT,FILE SIZE,UPLOAD_SIZE,PARENT_IO,USN,USER_ID,CORP_ID,CREATE_DATE,MODIFY_DATE,STATUS,VERSION,COME FROM, FILE PATH,IS_SHARE,GR OUP DESC,GROUP IO,ROOT USN,ATTENTION COUNT,FILE MDS,FILE LABEL,FILE DESC,FILE EXTENDS,DEVICE ID FROM CLOUDP FILE 9 WHERE FILE NAME LIK E '%'1111 党打1 2# 5051开共CTR E方案我第11% AND FILE-TYPE=2 AND OISK-TYPE=1 AND PARENT-ID=29BFB405EE2F70EDE053D5B6010A566D AND ROOT_USN=186879 AND STATUS=1 ORDER BY id"
                                +"<p1>asdf||</p2>\r\n" +
                                "<a>ggasdfasdf||</a>\r\n" +
                                "AS||C\r\n"
                                ;

                        syslog.log(0, msg);
//                log.info("成功发送消息:{} 碎觉碎觉", msg);
//                break;
                Thread.sleep(10L);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @GetMapping("/oomStop")
    public void oomStop(){
        flag=false;
        log.info("消息外发关闭");
    }


    @GetMapping("/ssh")
    public void ssh(){
        try {
            Process process = Runtime.getRuntime().exec("capaactl restart svc_report");
            process.waitFor();

            if (process.exitValue() == 0) {
                System.out.println("命令执行成功");
            } else {
                System.out.println("命令执行失败");
            }
        } catch (Exception e ) {
            log.error(e.getMessage());
        }
    }


    private static final int BATCH_SIZE=20000;

    private static SyslogIF syslog = null;
    @GetMapping("/sendMsg")
    public void sendMsg(){
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
//                        Thread.sleep(1000L);
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
        String msg = "|BSEC_DAS_DBSEC_CEF:|捕获时间:|:|:::" + TimeUtils.getStrByTime(System.currentTimeMillis(), TimeFormatEnum.formatter) + " |数据库IP:10.1.182.216 |数据库端口 :11521 |服务(实列)名 :scglib |数据库名称:N/A |客户端IP:10.1.182.136 |客户端端口:56098 |客户端MAC:C45444616D2E |客户端工具:JDBC THIN CLIENT |操作系统用户:richmail |应用用户名:N/A |数据库用户:CLOUDP |风险类型:gggggggg |风险级别:高 |规则名称:开打开打 |操作类型:SELECT |受影响对象:SGCCDB.CLOUDP.CLOUDP FILE_9 |响应时间:1182 |执行结果:" + (System.nanoTime() % 2 == 0 ? "失败" : "成功") + " |影响行数:88 |SQL语句:SELECT APP_FILE_ID，OFS_FILE_ID，FILE_NAME，FILE_LEVEL，FILE_COUNT，OISK_TYPE，FILE_TYPE,FILE SORT,FILE SIZE,UPLOAD_SIZE,PARENT_IO,USN,USER_ID,CORP_ID,CREATE_DATE,MODIFY_DATE,STATUS,VERSION,COME FROM, FILE PATH,IS_SHARE,GR OUP DESC,GROUP IO,ROOT USN,ATTENTION COUNT,FILE MDS,FILE LABEL,FILE DESC,FILE EXTENDS,DEVICE ID FROM CLOUDP FILE 9 WHERE FILE NAME LIK E '%'1111 党打1 2# 5051开共CTR E方案我第11% AND FILE-TYPE=2 AND OISK-TYPE=1 AND PARENT-ID=29BFB405EE2F70EDE053D5B6010A566D AND ROOT_USN=186879 AND STATUS=1 ORDER BY id"
                + "|客户端操作系统用户:userTEST"
                + "|主机名:GGGG"
                + "|应用用户:AAAA"
                + "|服务器IP:127.0.0.1"
                + "|服务器端口:888"
                + "|访问对象:OBJECT"
                + "|数据库名:USER"
                + System.nanoTime();
        return msg;
    }
    public static void main(String[] args) {
        String msg="15507 ，drop";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(msg);
        System.out.println(m.replaceAll("").trim());
    }

}
