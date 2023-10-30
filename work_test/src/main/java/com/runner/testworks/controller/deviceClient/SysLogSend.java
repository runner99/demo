package com.runner.testworks.controller.deviceClient;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author weizhenqiang
 * @date 2023/6/30 15:43
 */

@Slf4j
public class SysLogSend {

    private static final String HOST = "192.168.52.201";
    private static final int PORT = 1468;

    public static void main(String[] args) {

        SyslogIF syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);
        syslog.getConfig().setMaxMessageLength(1024000);

//        绍兴大数据对接奇安全信防火墙
//        String msg="asdf devid=\"\" dname=\"\" serial=\"\" module=\"flow\" severity=\"\" vsys=\"\" type=\"traffic-end\" session_id=\"\" time=\"\" duration=\"\" addr_src=\"\" addr_dst=\"\" nataddr_src=\"\" nataddr_dst=\"\" port_src=\"\" port_dst=\"\" natport_src=\"\" natport_dst=\"\" action=\"deny\" appname=\"\" rule=\"\" proto=\"\" session_time=\"\" sess_nth=\"\" sess_dev_id=\"\" zone_src=\"\" zone_dst=\"\" locale_src=\"\" locale_dst=\"\" user_src=\"\" user_dst=\"\" app_category=\"\" app_risk=\"\" asset_os_src=\"\" asset_os_dst=\"\" asset_name_src=\"\" asset_name_dst=\"\" asset_type_src=\"\" asset_type_dst=\"\" focus_type=\"\" profile=\"\" non_standard_port=\"\" bytes_sent=\"\" bytes_received=\"\" pkts_sent=\"\" pkts_received=\"\" total_sess=\"\" from_tunnel=\"\" to_tunnel=\"\"\\n";
        //高危
//        String msg= "devid=\"3\" dname=\"鲲鹏出口\" serial=\"8c151d31ad23faa027b5858bc23ff8e7365baee2\" module=\"flow\" severity=\"info\" vsys=\"root-vsys\" type=\"traffic-end\" session_id=\"0\" time=\"\" duration=\"0\" addr_src=\"192.168.10.100\" addr_dst=\"62.207.132.137\" nataddr_src=\"::\" nataddr_dst=\"::\" port_src=\"55147\" port_dst=\"445\" natport_src=\"0\" natport_dst=\"0\" action=\"deny\" appname=\"SMB\" rule=\"deny-445\" proto=\"TCP\" session_time=\"0\" sess_nth=\"0\" sess_dev_id=\"0\" zone_src=\"鲲鹏B网-测试\" zone_dst=\"to_ops_外网\" locale_src=\"内网\" locale_dst=\"荷兰\" user_src=\"\" user_dst=\"\" app_category=\"APP_NETWORK\" app_risk=\"3\" asset_os_src=\"\" asset_os_dst=\"\" asset_name_src=\"\" asset_name_dst=\"\" asset_type_src=\"\" asset_type_dst=\"\" focus_type=\"NO\" profile=\"\" non_standard_port=\"NO\" bytes_sent=\"0\" bytes_received=\"0\" pkts_sent=\"0\" pkts_received=\"0\" total_sess=\"0\" from_tunnel=\"\" to_tunnel=\"\"";


      try {
            while (true){

//                //        安华金和动态脱敏
//                String msg="2022-11-01T11:41:17+08:00 DBSEC_DAS DBSEC 受影响对象: mysql.A.TEST01;  #SQL标识:12816206170203243825   #应答错误号:0   #应答错误信息:   #影响行数:1   #响应时间:54   结果集:   #执行结果:成功   #风险类型:违反操作规则   #风险级别:中   #规则名称:select规则   #应用客户端IP:192.134.1.1   #应用客户端端口:0   #应用用户名:   #应用会话标识:   #被保护数据库:测试mysql   #流水标识:2211011141010011030   " +
//                        "#捕获时间:"+ LocalDate.now()+" "+ LocalTime.now().toString().substring(0, 8) +"   #SQL:select * from tab;  #操作类型:SELECT   #语句长度:86   #会话标识:4049961760031000000   " +
//                        "#会话开始时间:"+LocalDate.now()+" "+ LocalTime.now().toString().substring(0, 8)+"   #客户端IP:10.11.24.21   #客户端端口:58359   #客户端MAC:30B0377A7D89   #数据库用户:ROOT   #客户端工具:MySQL Connector/J   #客户端主机名称:nwonknu   #操作系统用户:nwonknu   #数据库IP:10.98.100.12   #数据库端口:13306   #数据库MAC:000C29108DB5  #数据库名称:A   #服务(实例)名:mysql   #被保护数据库类型:MySQL";

//                访问事件
                String msg="{\"o_name\":\"zhang1\",\"o_schema\":\"\",\"s_identity\":\"\",\"tenant_id\":\"\",\"r_risk_description\":\"正常\",\"e_cap_dev_id\":\"\",\"o_svr_ip\":\"192.168.52.211\",\"r_risk\":1,\"f_err\":\"1\",\"c_time\":"+ System.currentTimeMillis()+",\"e_time\":"+System.currentTimeMillis()+",\"review_status\":2,\"s_db_user\":\"ASSET\",\"e_cap_dev_name\":\"devd2\",\"o_variable\":\"\",\"s_app_name\":\"LIBMYSQL\",\"s_dev_port\":56939,\"s_t_app_name\":\"LIBMYSQL\",\"o_type\":\"MySQL\",\"e_cap_dev_type\":\"dfd2\",\"f_running_time\":0,\"f_err_description\":\"失败\",\"s_dev_mac\":\"\",\"o_svr_port\":13306,\"o_object\":\"\",\"e_type\":\"db_access\",\"s_dev_ip\":\"192.168.11.19\",\"b_action\":\"select\",\"b_action_category\":\"DML\",\"s_os_user\":\"\",\"c_session\":\"9003_1607664577657508\",\"f_affected\":100,\"s_t_account\":\"ASSET\",\"s_dev_name\":\"\",\"o_standard\":\"select * from zhang.zhangmc_api_info\",\"e_category\":\"alert\",\"r_risk_type\":\"\",\"r_matched_name\":\"\",\"o_statement\":\"\",\"r_response\":\"通过\"}\n";


                syslog.log(0, URLDecoder.decode(msg, "utf-8"));
//                log.info("成功发送消息：{} 碎觉碎觉",msg);
//                break;
//                Thread.sleep(1000L);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }





}
