package com.runner.testworks.controller.xzlt.enums;

/**
 * @author weizhenqiang
 * @date 2023/11/9 11:36
 */
public enum ThirdOssPrefixEnum {

    /**
     * 赛猊腾龙终端DLP
     */
    OTHERSYNDLP("OTHERSYNDLP"),

    /**
     * 青笠API监测
     */
    QINGLI("qingli");

    private String value;

    public String getValue() {
        return value;
    }

    private ThirdOssPrefixEnum(String value) {
        this.value = value;
    }
}
