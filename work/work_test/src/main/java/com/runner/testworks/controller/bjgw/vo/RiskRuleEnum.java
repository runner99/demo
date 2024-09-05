package com.runner.testworks.controller.bjgw.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weizhenqiang
 * @date 2023/8/22 15:15
 */
@AllArgsConstructor
@Getter
public enum RiskRuleEnum {

    POJIE("暴力破解"),
    ZHUANGKU("撞库");
    private String type;

}
