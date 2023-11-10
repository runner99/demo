package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/2 22:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafetyprofileVo {
    private Integer page;
    private Integer size;
    private Integer total;
    private List<Item> records;
}
