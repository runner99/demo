package com.runner.testworks.controller.deviceClient;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

//        安华金和动态脱敏
//        String msg="2022-11-01T11:41:17+08:00 DBSEC_DAS DBSEC 受影响对象: mysql.A.TEST01;  #012SQL标识:12816206170203243825   #012应答错误号:0   #012应答错误信息:   #012影响行数:1   #012响应时间:54   结果集:   #012执行结果:成功   #012风险类型:违反操作规则   #012风险级别:中   #012规则名称:select规则   #012应用客户端IP:192.134.1.1   #012应用客户端端口:0   #012应用用户名:   #012应用会话标识:   #012被保护数据库:测试mysql   #012流水标识:2211011141010011030   #012捕获时间:2022-11-01 11:41:01   #012SQL:select * from tab;  #012操作类型:SELECT   #012语句长度:86   #012会话标识:4049961760031000000   #012会话开始时间:2023-08-15 16:15:11   #012客户端IP:10.10.24.69   #012客户端端口:58359   #012客户端MAC:30B0377A7D89   #012数据库用户:ROOT   #012客户端工具:MySQL Connector/J   #012客户端主机名称:nwonknu   #012操作系统用户:nwonknu   #012数据库IP:10.10.100.220   #012数据库端口:13306   #012数据库MAC:000C29108DB5  #012数据库名称:A   #012服务(实例)名:mysql   #012被保护数据库类型:MySQL";


      try {
            while (true){
                String msg="{\"e_category\":\"alert\",\n" +
                        "\"e_type\":\"db_access\",\n" +
                        "\"s_os_user\":\"\",\n" +
                        "\"s_db_user\":\"\",\n" +
                        "\"s_dev_ip\":\"172.23.15.5\",\n" +
                        "\"s_dev_port\":46312,\n" +
                        "\"s_dev_name\":\"\",\n" +
                        "\"c_time\":"+System.currentTimeMillis()+",\n" +
                        "\"c_session\":\"3285590481206772953\",\n" +
                        "\"b_action\":\"2\",\n" +
                        "\"o_name\":\".....................1\",\n" +
                        "\"o_svr_ip\":\"10.23.195.99\",\n" +
                        "\"o_svr_port\":23308,\n" +
                        "\"o_statement\":\"insert.into.cg_renyuaninfo(budget_code,biaoduanno,biaoduanguid,biaoduanname,frirstdanweiname,unitorgnum,renyuanzc,renyuanname,renyuanidcard.,renyuantel,load_time.).values('3306043440112023X01097','A3205820001004079001001','10815ebc-8c4b-4df6-b199-8b933ed3081f','.................................','..............................','','...............','.........','330424198509032212','13858538866','2023-07-14.14:07:27')\",\n" +
                        "\"r_matched_name\":\"MySQL_.......................................\",\n" +
                        "\"r_risk_type\":\".....................\",\n" +
                        "\"r_risk\":2,\n" +
                        "\"f_affected\":0,\n" +
                        "\"f_running_time\":1127,\n" +
                        "\"f_err\":\"0\",\n" +
                        "\"o_standard_id\":\"170893195864759403222 7d0d 0a                             2\"\n" +
                        "}";
                syslog.log(0, URLDecoder.decode(msg, "utf-8"));
                log.info("成功发送消息：{} 碎觉碎觉",msg);
//                break;
                Thread.sleep(1000L);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }





}
