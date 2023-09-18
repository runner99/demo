package com.runner.testworks.controller.bjgw.vo;

/**
 * @author weizhenqiang
 * @date 2023/8/24 10:54
 */
public enum RiskRuleDescType {
    TEXT("text"),
    INPUT("input");

    private final String value;

    RiskRuleDescType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
