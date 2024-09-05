package com.runner.testworks.controller.shangyu;

import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.net.URLDecoder;

/**
 * @author weizhenqiang
 * @date 2023/9/18 23:29
 */
@Slf4j
public class ShangYuSysLogSend {
    private static final String HOST = "192.168.152.142";
    private static final int PORT = 1468;

    public static void main(String[] args) {

        SyslogIF syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);

        //原始日志
//        String msg="{\"e_category\":\"alert\",\"e_type\":\"db_access\",\"s_os_user\":\"\",\"s_db_user\":\"bmfw_jyzx\",\"s_dev_ip\":\"10.23.226.162\",\"s_dev_port\":\"54330\",\"s_dev_name\":\"\",\"c_time\":\"1695022221682\",\"c_session\":\"3180324021544814077\",\"b_action\":\"31\",\"o_name\":\".....................1\",\"o_svr_ip\":\"10.23.195.99\",\"o_svr_port\":\"23308\",\"o_statement\":\"use.bmfw_jyzx\",\"r_matched_name\":\"MySQL_.......................................\",\"r_risk_type\":\".....................\",\"r_risk\":\"1\",\"f_affected\":\"0\",\"f_running_time\":\"2038\",\"f_err\":\"0\",\"o_standard_id\":\"147598667122841297\"}";

//        修改后
        long timeMillis = System.currentTimeMillis();
        String msg="{\"e_category\":\"alert\",\"e_type\":\"db_access\",\"s_os_user\":\"\",\"s_db_user\":\"bmfw_jyzx\",\"s_dev_ip\":\"10.23.226.162\",\"s_dev_port\":54330,\"s_dev_name\":\"\",\"c_time\":"+timeMillis+",\"c_session\":\"3180324021544814077\",\"b_action\":\"31\",\"o_name\":\".....................1\",\"o_svr_ip\":\"10.23.195.99\",\"o_svr_port\":23308,\"o_statement\":\"use.bmfw_jyzx\",\"r_matched_name\":\"MySQL_.......................................\",\"r_risk_type\":\".....................\",\"r_risk\":1,\"f_affected\":0,\"f_running_time\":2038,\"f_err\":\"0\",\"o_standard_id\":\"147598667122841297\"}";
        System.out.println(msg.replaceAll("\n",""));
        try {
            while (true){
                syslog.log(0, URLDecoder.decode(msg, "utf-8"));
                log.info("成功发送消息：{} 碎觉碎觉",msg);
                Thread.sleep(1000L);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
