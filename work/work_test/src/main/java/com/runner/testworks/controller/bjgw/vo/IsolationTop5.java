package com.runner.testworks.controller.bjgw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/8/4 16:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsolationTop5 {
    private String ip;
    private Integer count;
}
