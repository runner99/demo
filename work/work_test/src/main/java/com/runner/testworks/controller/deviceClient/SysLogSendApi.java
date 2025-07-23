package com.runner.testworks.controller.deviceClient;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import com.alibaba.fastjson.JSONObject;

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
public class SysLogSendApi {

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
    private static String buildMessage() {

        JSONObject json = new JSONObject();



//        json.put("o_svr_ip", "192.168.52.204");
//        json.put("o_svr_port", 18443);
//        json.put("o_standard", "/tenant/initten/soc/dict");

        json.put("o_svr_ip", "127.0.1.1");
        json.put("o_svr_port", 11);
        json.put("o_standard", "/asdffdsa");


        // 添加所有字段
        json.put("o_obj_type", "");
        json.put("s_msg_list", "");
        json.put("o_objects", "");
        json.put("s_t_dev_mac", "");
        json.put("target_app_name", "");
        json.put("target_app_key", "");
        json.put("o_req_param", "");
        json.put("s_dev_mac", "");
        json.put("review_remark", "自动审阅");
        json.put("o_resp_len", 1398);
        json.put("o_object", "");
        json.put("e_type", "db_access");
        json.put("e_cap_dev_ip", "");
        json.put("o_instance_name", "");
        json.put("attack_phase", "");
        json.put("raw", "");
        json.put("index", System.currentTimeMillis());
        json.put("o_set_cookie", "");
        json.put("o_protocol", "Https");
        json.put("r_risk_name", "");
        json.put("s_app_path", "");
        json.put("s_dev_name", "");
        json.put("r_sensitive_level", 0);
        json.put("s_identity", "192.168.30.62:50825");
        json.put("s_app_hash", "");
        json.put("metrics_description", "3230");
        json.put("c_time", System.currentTimeMillis());
        json.put("e_cap_dev_name", "");
        json.put("s_dev_port", 50825);
        json.put("s_t_dev_ip", "");
        json.put("resp_data_tag", "");
        json.put("s_t_app_name", "");
        json.put("o_standard_id", "g3c1avuyxph5t");
        json.put("o_user_agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
        json.put("identity", "192.168.30.62:50825");
        json.put("o_table_names", "");
        json.put("review_status", 2);
        json.put("b_action", "POST");
        json.put("b_action_category", "OTHER");
        json.put("o_cookie", "{\"SESSION\":\"acc2e328-c2b8-4314-9bb0-dc7385dc5da7\",\"mccentersessionid\":\"node0dvjo8hj35xx01o7twurecf2i74258.node0\"}");
        json.put("is_ftf", 0);
        json.put("o_http_code", "200");
        json.put("r_matched_id", "");
        json.put("s_security_user", "");
        json.put("r_risk_type", "");
        json.put("o_resp_content_type", "application/json");
        json.put("o_resp_header", "{\"Strict-Transport-Security\":\"max-age=315360000; includeSubDomains\",\"X-Frame-Options\":\"DENY,SAMEORIGIN\",\"Pragma\":\"no-cache\",\"X-Content-Type-Options\":\"nosniff\",\"Content-Length\":\"974\",\"Content-Type\":\"application/json\",\"Connection\":\"keep-alive\",\"Cache-Control\":\"no-cache, no-store, max-age=0, must-revalidate\",\"X-XSS-Protection\":\"1; mode=block\",\"Server\":\"nginx\",\"Date\":\"Mon, 03 Mar 2025 06:03:29 GMT\",\"Expires\":\"0\"}");
        json.put("c_time_mico", 0);
        json.put("o_schema", "");
        json.put("o_name", "ceshi1");
        json.put("r_risk", 0);
        json.put("o_app_uid", "");
        json.put("o_variable", "[]");
        json.put("o_resp_msg", "{\"code\":0,\"message\":\"success\",\"data\":{\"id\":360102199710191616,\"type\":1,\"auditConfigId\":1425862399942657,\"assetDatabaseId\":1,\"assetDatabaseName\":\"206\",\"collectRecord\":2,\"returnRows\":1,\"returnLength\":2,\"objectType\":3,\"objectName\":\"mc_afwconfig\",\"db\":\"\",\"schema\":\"afw\",\"table\":\"mc_afwconfig\",\"createUser\":\"security\",\"updateUser\":\"security\",\"createTime\":\"2025-02-26T07:24:47.000+00:00\",\"updateTime\":\"2025-02-26T07:24:47.000+00:00\",\"auditMod\":1,\"dataSources\":null,\"databaseStructure\":\"afwmc_afwconfig\",\"assetType\":\"Mysql\",\"assetAddress\":\"192.168.230.206:3306\",\"auditModLabel\":null,\"assetDatabaseList\":null,\"returnResult\":\"1行、 2KB\",\"dbUser\":null,\"objTypeList\":[{\"id\":3,\"auditModId\":1425862399967232,\"objType\":\"FUNCTION\",\"objValue\":\"123\",\"objName\":\"123\",\"createUser\":\"security\",\"createTime\":\"2025-02-25T16:00:00.000+00:00\"}],\"cliIp\":null,\"endIp\":null,\"appName\":null,\"opType\":null,\"opTypeList\":[],\"keyWord\":null,\"auditModId\":1425862399967232,\"assetInfo\":\"206.afw\"},\"success\":true}");
        json.put("o_app_mac", "");
        json.put("f_running_time", 170);
        json.put("o_service_name", "");
        json.put("s_app_sign", "");
        json.put("r_sensitive_type", "");
        json.put("stream_time_interval", -1);
        json.put("o_req_len", 994);
        json.put("s_dev_cert", "");
        json.put("s_os_user", "");
        json.put("e_time", System.currentTimeMillis());
        json.put("data_tag_level", "");
//        json.put("o_req_header", "{\"Accept-Language\":\"zh-CN,zh;q=0.9\",\"Accept-Encoding\":\"gzip, deflate, br\",\"Cookie\":\"mccentersessionid=node0dvjo8hj35xx01o7twurecf2i74258; SESSION=acc2e328-c2b8-4314-9bb0-dc7385dc5da7; mccentersessionid=node0dvjo8hj35xx01o7twurecf2i74258.node0\",\"Referer\":\"https://192.168.52.65:18443/tenant/initten/app/protect/audit/audit_configuration/detail/1425862399942657\",\"Sec-Fetch-Mode\":\"cors\",\"Sec-Fetch-Dest\":\"empty\",\"Sec-Fetch-Site\":\"same-origin\",\"Origin\":\"https://192.168.52.65:18443\",\"Host\":\"192.168.52.65:18443\",\"Content-Length\":\"36\",\"X-Resource-Code\":\"auditConfig\",\"sec-ch-ua\":\"\\\"Not A(Brand\\\";v=\\\"99\\\", \\\"Google Chrome\\\";v=\\\"121\\\", \\\"Chromium\\\";v=\\\"121\\\"\",\"Accept\":\"application/json, text/plain, */*\",\"Connection\":\"keep-alive\",\"Content-Type\":\"application/json\",\"sec-ch-ua-mobile\":\"?0\",\"User-Agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36\",\"sec-ch-ua-platform\":\"\\\"Windows\\\"\"}");
        json.put("r_matched_name", "");
        json.put("o_statement", "/tenant/initten/api/audit/1.0.0/config/detail");
        json.put("r_response", "");
        json.put("business_type_id", 0);
        json.put("r_risk_description", "");
        json.put("s_identity_name", "未知身份");
        json.put("tool_sql", 0);
        json.put("o_referrer", "https://192.168.52.65:18443/tenant/initten/app/protect/audit/audit_configuration/detail/1425862399942657");
        json.put("f_err", "0");
        json.put("req_data_tag", "");
        json.put("s_db_user", "");
        json.put("o_uid", "");
        json.put("o_file_path", "");
        json.put("s_t_dev_port", 0);
        json.put("s_app_name", "NULL");
        json.put("o_type", "Https");
        json.put("f_err_description", "");
        json.put("service_unit_id", 0);
        json.put("s_sql_truncate", 0);
        json.put("e_terminal_id", "1894668627190337538");
        json.put("o_columns", "");
        json.put("s_dev_ip", "192.168.30.62");
        json.put("o_column_names", "");
        json.put("o_app_domain", "192.168.52.65:18443");
        json.put("c_time_result", 0);
        json.put("tf_trans", "");
        json.put("o_req_body", "{\"auditConfigId\":\"1425862399942657\"}");
        json.put("c_session", "77772381");
        json.put("s_t_account", "");
        json.put("s_dev_ip_geo", "");
        json.put("request_info", "");
        json.put("s_sql_size", 45);
        json.put("e_category", "common");
        json.put("o_req_content_type", "application/json");
        json.put("s_identity_group_name", "");
        json.put("r_audit", 1);
        json.put("s_per_cert", "");
        json.put("o_reg_field_param", "");
        json.put("o_reg_field_body", "auditConfigId");
        json.put("o_resp_field_body", "code、message、data、id、type、auditConfigId、assetDatabaseId、assetDatabaseName、collectRecord、returnRows、returnLength、objectType、objectName、db、schema、table、createUser、updateUser、createTime、updateTime、auditMod、dataSources、databaseStructure、assetType、assetAddress、auditModLabel、assetDatabaseList、returnResult、dbUser、objTypeList、cliIp、endIp、appName、opType、opTypeList、keyWord、auditModId、assetInfo、success");

        return JSON.toJSONString(json);
    }


}
