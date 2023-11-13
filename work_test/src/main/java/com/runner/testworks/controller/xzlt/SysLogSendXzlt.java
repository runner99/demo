package com.runner.testworks.controller.xzlt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSendXzlt {

    private static final String HOST = "192.168.52.181";
    private static final int PORT = 1469;

    public static void main(String[] args) {

        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UDP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {

                        //赛猊腾龙终端DLP   UDP对接
                        long currented = System.currentTimeMillis();
//                        String msg = "{\"o_name\":\"zhang1\",\"o_schema\":\"\",\"s_identity\":\"\",\"tenant_id\":\"\",\"r_risk_description\":\"正常\",\"e_cap_dev_id\":\"\",\"o_svr_ip\":\"192.168.61.14\",\"r_risk\":1,\"f_err\":\"1\",\"c_time\":" + currented + ",\"e_time\":" + currented + ",\"review_status\":2,\"s_db_user\":\"ASSET\",\"e_cap_dev_name\":\"devd2\",\"o_variable\":\"\",\"s_app_name\":\"LIBMYSQL\",\"s_dev_port\":56939,\"s_t_app_name\":\"LIBMYSQL\",\"o_type\":\"MySQL\",\"e_cap_dev_type\":\"dfd2\",\"f_running_time\":0,\"f_err_description\":\"失败\",\"s_dev_mac\":\"\",\"o_svr_port\":3309,\"o_object\":\"\",\"e_type\":\"db_access\",\"s_dev_ip\":\"192.168.11.19\",\"b_action\":\"select\",\"b_action_category\":\"DML\",\"s_os_user\":\"\",\"c_session\":\"9003_1607664577657508\",\"f_affected\":100,\"s_t_account\":\"ASSET\",\"s_dev_name\":\"\",\"o_standard\":\"select * from zhang.zhangmc_api_info\",\"e_category\":\"alert\",\"r_risk_type\":\"\",\"r_matched_name\":\"\",\"o_statement\":\"\",\"r_response\":\"通过\"}\n";
//String msg="{\"r_risk\":0,\"f_err\":\"0\",\"e_category\":\"common\",\"e_type\":\"db_access\",\"s_os_user\":\"DESKTOP-VLI3VTC\\00\",\"s_dev_ip\":\"10.218.21.135\",\"s_dev_mac\":\"08-3A-88-69-99-E3\",\"b_action\":\"上传\",\"o_name\":\"测试.docx\"}";
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("e_category", "common");
                        map.put("e_type", "db_access");
                        map.put("r_risk", 0);
                        map.put("f_err", "0");
                        String msg = JSON.toJSONString(map);

                        syslog.log(0, msg);
                        log.info("成功发送消息:{} 碎觉碎觉", msg);
                        Thread.sleep(1000L);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


}
