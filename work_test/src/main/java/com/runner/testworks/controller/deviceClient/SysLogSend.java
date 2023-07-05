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

    private static final String HOST = "192.168.52.181";
    private static final int PORT = 1469;

    public void generate() {
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UDP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);

//        String msg="asdf devid=\"\" dname=\"\" serial=\"\" module=\"flow\" severity=\"\" vsys=\"\" type=\"traffic-end\" session_id=\"\" time=\"\" duration=\"\" addr_src=\"\" addr_dst=\"\" nataddr_src=\"\" nataddr_dst=\"\" port_src=\"\" port_dst=\"\" natport_src=\"\" natport_dst=\"\" action=\"deny\" appname=\"\" rule=\"\" proto=\"\" session_time=\"\" sess_nth=\"\" sess_dev_id=\"\" zone_src=\"\" zone_dst=\"\" locale_src=\"\" locale_dst=\"\" user_src=\"\" user_dst=\"\" app_category=\"\" app_risk=\"\" asset_os_src=\"\" asset_os_dst=\"\" asset_name_src=\"\" asset_name_dst=\"\" asset_type_src=\"\" asset_type_dst=\"\" focus_type=\"\" profile=\"\" non_standard_port=\"\" bytes_sent=\"\" bytes_received=\"\" pkts_sent=\"\" pkts_received=\"\" total_sess=\"\" from_tunnel=\"\" to_tunnel=\"\"\\n";
        //高危
        String msg= "devid=\"3\" dname=\"鲲鹏出口\" serial=\"8c151d31ad23faa027b5858bc23ff8e7365baee2\" module=\"flow\" severity=\"info\" vsys=\"root-vsys\" type=\"traffic-end\" session_id=\"0\" time=\"\" duration=\"0\" addr_src=\"192.168.10.100\" addr_dst=\"62.207.132.137\" nataddr_src=\"::\" nataddr_dst=\"::\" port_src=\"55147\" port_dst=\"445\" natport_src=\"0\" natport_dst=\"0\" action=\"deny\" appname=\"SMB\" rule=\"deny-445\" proto=\"TCP\" session_time=\"0\" sess_nth=\"0\" sess_dev_id=\"0\" zone_src=\"鲲鹏B网-测试\" zone_dst=\"to_ops_外网\" locale_src=\"内网\" locale_dst=\"荷兰\" user_src=\"\" user_dst=\"\" app_category=\"APP_NETWORK\" app_risk=\"3\" asset_os_src=\"\" asset_os_dst=\"\" asset_name_src=\"\" asset_name_dst=\"\" asset_type_src=\"\" asset_type_dst=\"\" focus_type=\"NO\" profile=\"\" non_standard_port=\"NO\" bytes_sent=\"0\" bytes_received=\"0\" pkts_sent=\"0\" pkts_received=\"0\" total_sess=\"0\" from_tunnel=\"\" to_tunnel=\"\"";
//mock
//        String msg= "Dec 26 20:13:17 172.24.213.201.devid=\"4\".dname=\"鲲鹏出口\".serial=\"...8c151d31ad23faa027b5858bc23ff8e7365baee2..\".module=\"flow\".severity=\"info\".vsys=\"root-vsys\".type=\"traffic-end\".session_id=\"0\".time=\"\".duration=\"0\".addr_src=\"192.168.10.100\".addr_dst=\"62.207.132.137\".nataddr_src=\"::\".nataddr_dst=\"::\".port_src=\"55147\".port_dst=\"445\".natport_src=\"0\".natport_dst=\"0\".action=\"deny\".appname=\"SMB\".rule=\"deny-445\".proto=\"TCP\".session_time=\"0\".sess_nth=\"0\".sess_dev_id=\"0\".zone_src=\"鲲鹏B网-测试\".zone_dst=\"to_ops_外网\".locale_src=\"内网\".locale_dst=\"荷兰\".user_src=\"\".user_dst=\"\".app_category=\"APP_NETWORK\".app_risk=\"3\".asset_os_src=\"\".asset_os_dst=\"\".asset_name_src=\"\".asset_name_dst=\"\".asset_type_src=\"\".asset_type_dst=\"\".focus_type=\"NO\".profile=\"\".non_standard_port=\"NO\".bytes_sent=\"0\".bytes_received=\"0\".pkts_sent=\"0\".pkts_received=\"0\".total_sess=\"0\".from_tunnel=\"\".to_tunnel=\"\"\n";

//        String msg="";
//        for (int i=0;i<360;i++){
//            msg+="哈";
//        }
//        System.out.println(msg.length());
        try {
            while (true){
                syslog.log(0, URLDecoder.decode(msg, "utf-8"));
//                log.info("成功发送消息：{} 碎觉碎觉",msg);
                Thread.sleep(5000L);
            }

        } catch (UnsupportedEncodingException e) {
            System.out.println("generate log get exception " + e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        new SysLogSend().generate();
    }


}
