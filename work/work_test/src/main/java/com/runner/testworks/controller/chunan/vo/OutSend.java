package com.runner.testworks.controller.chunan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/7/21 18:16
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutSend {
    private String user;
    private Long timestamp;
    private String sign;
    private List<OutSendEvent> dataValues;
}
