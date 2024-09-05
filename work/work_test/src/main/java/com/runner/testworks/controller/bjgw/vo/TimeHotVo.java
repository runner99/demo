package com.runner.testworks.controller.bjgw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/8/4 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeHotVo {
    private Integer low;
    private Integer high;
    private String time;
    private Integer current;
}
