package com.runner.testworks.controller.quzhou;

import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSendQuzhou {

    private static final String HOST = "192.168.52.201";
    private static final int PORT = 1468;

    public static void main(String[] args) {

        Integer core = 1;
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);

        ExecutorService executorService = Executors.newFixedThreadPool(core);

        for (int i = 0; i < core; i++) {
            executorService.execute(() -> {

                try {
                    while (true) {
                        //  标准化测试数据
                        long currented = System.currentTimeMillis();
//                        十月数据
//                        long currented = 1696435200000L;
//                        十一月数据
//                        long currented = 1700496000000L;

//                        告警事件 o_standard_id
                        String msg = "{\"o_standard_id\":\"testsqlid\",\"o_name\":\"zhang1\",\"o_schema\":\"\",\"s_identity\":\"\",\"tenant_id\":\"\",\"r_risk_description\":\"正常\",\"e_cap_dev_id\":\"\",\"o_svr_ip\":\"192.168.61.14\",\"r_risk\":2,\"f_err\":\"999\",\"c_time\":" + currented + ",\"e_time\":" + currented + ",\"review_status\":2,\"s_db_user\":\"ASSET\",\"e_cap_dev_name\":\"devd2\",\"o_variable\":\"\",\"s_app_name\":\"LIBMYSQL\",\"s_dev_port\":56939,\"s_t_app_name\":\"LIBMYSQL\",\"o_type\":\"MySQL\",\"e_cap_dev_type\":\"dfd2\",\"f_running_time\":0,\"f_err_description\":\"失败\",\"s_dev_mac\":\"\",\"o_svr_port\":3309,\"o_object\":\"\",\"e_type\":\"db_logon\",\"s_dev_ip\":\"192.168.11.19\",\"b_action\":\"\",\"b_action_category\":\"DDL\",\"s_os_user\":\"\",\"c_session\":\"9003_1607664577657508\",\"f_affected\":100,\"s_t_account\":\"ASSET\",\"s_dev_name\":\"\",\"o_standard\":\"select * from zhang.zhangmc_api_info\",\"e_category\":\"alert\",\"r_risk_type\":\"数据泄露11\",\"r_matched_name\":\"\",\"o_statement\":\"select * from user\",\"r_response\":\"通过\"}\n";
//                        普通事件
//                        String msg = "{\"o_name\":\"zhang1\",\"o_schema\":\"\",\"s_identity\":\"\",\"tenant_id\":\"\",\"r_risk_description\":\"正常\",\"e_cap_dev_id\":\"\",\"o_svr_ip\":\"192.168.61.14\",\"r_risk\":0,\"f_err\":\"999\",\"c_time\":" + currented + ",\"e_time\":" + currented + ",\"review_status\":2,\"s_db_user\":\"ASSET\",\"e_cap_dev_name\":\"devd2\",\"o_variable\":\"\",\"s_app_name\":\"LIBMYSQL\",\"s_dev_port\":56939,\"s_t_app_name\":\"LIBMYSQL\",\"o_type\":\"MySQL\",\"e_cap_dev_type\":\"dfd2\",\"f_running_time\":0,\"f_err_description\":\"失败\",\"s_dev_mac\":\"\",\"o_svr_port\":3309,\"o_object\":\"\",\"e_type\":\"db_access\",\"s_dev_ip\":\"192.168.11.19\",\"b_action\":\"select\",\"b_action_category\":\"DML\",\"s_os_user\":\"\",\"c_session\":\"9003_1607664577657508\",\"f_affected\":100,\"s_t_account\":\"ASSET\",\"s_dev_name\":\"\",\"o_standard\":\"select * from zhang.zhangmc_api_info\",\"e_category\":\"common\",\"r_risk_type\":\"高危操作\",\"r_matched_name\":\"\",\"o_statement\":\"\",\"r_response\":\"通过\"}\n";
                        syslog.log(0, msg);
//                            log.info("成功发送消息:{} 碎觉碎觉", msg);
//                break;
                            Thread.sleep(1000L);
                    }

                } catch (Exception e) {
                    log.error("" + e);
                }


            });
        }


    }


}
