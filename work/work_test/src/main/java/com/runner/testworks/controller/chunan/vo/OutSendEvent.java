package com.runner.testworks.controller.chunan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/7/21 17:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutSendEvent {

    /**
     * 事件ID
     * 字符
     * 必填
     */
    private String incidentID;

    /**
     * 事件名称
     * 字符
     * 必填
     */
    private String incidentName;

    /**
     * 威胁等级
     * 枚举
     * 必填
     * 威胁等级(低：Low, 中：Medium, 高：High)
     */
    private String threatSeverity;

    /**
     * 事件等级
     * 枚举
     * 必填
     * 威胁等级(specialEvent，bigEvent，largerEvent，commonEvent分别对应特别重大事件、重大事件、较大事件和一般事件)
     */
    private String eventsThreatSeverity;

    /**
     * 受害者
     * 字符
     * 必填
     */
    private String victim;

    /**
     * 攻击IP
     * 字符
     * 必填
     */
    private String attacker;

    /**
     * eventOther
     * 枚举
     * 必填
     * 事件类型，枚举值传对应字段ID，字段ID见下方补充（拒绝服务攻击、扫描探测、隐患利用、有害程序、可用性、信息篡改、黑页、暗链、反共、博彩、色情、非法广告、高级威胁、信息假冒、信息泄露、信息窃取、信息丢失、DNS污染、Wifi劫持、BGP劫持、广播欺诈、配置错误、合规缺陷、访问异常、流量异常、漏洞、其他）
     */
    private String eventsType;

    /**
     * 事件标签
     * 数组
     * 可选
     */
    private List<String> incidentTag;

    /**
     * 举证信息
     * 字符
     * 可选
     */
    private String evidence;

    /**
     * 事件描述
     * 字符
     * 必填
     */
    private String describe;

    /**
     * 单位名称
     * 字符
     * 必填
     */
    private String unitName;

    /**
     * 事件相关的域名
     * 数组
     * 可选
     */
    private List<String> assetDomain;

    /**
     * 事件相关IPV4
     * 数组
     * 可选
     */
    private List<String> assetIpv4;

    /**
     * 事件相关IPV6
     * 数组
     * 可选
     */
    private List<String> assetIpv6;

    /**
     * 事件相关Mac地址
     * 数组
     * 可选
     */
    private List<String> assetMac;

    /**
     * 事件起始时间
     * 字符
     * 必填
     */
    private String startTime;

    /**
     * 事件结束时间
     * 字符
     * 必填
     */
    private String endTime;

    /**
     * 发现厂商
     * 字符
     * 可选
     */
    private String discoverFactory;

    /**
     * 事件来源
     * 字符
     * 必填
     */
    private String eventFrom;

    /**
     * 数据来源
     * 字符
     * 必填
     */
    private String dataFrom;

    /**
     * 验证状态
     * 枚举
     * 必填
     */
    private String judgeState;

    /**
     * 处置状态
     * 枚举
     * 必填
     */
    private String noticeState;

    /**
     * 原始事件
     * 字符
     * 必填
     */
    private String rawEvent;




}
