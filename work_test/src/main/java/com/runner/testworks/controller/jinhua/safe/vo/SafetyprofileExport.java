package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/3 11:11
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SafetyprofileExport {
    /**
     * id集合
     */
    private List<Integer> idList;
    /**
     * 时间段
     */
    private String timeSlot;
    /**
     * 是否重点项 0否 1是。默认0
     */
    private Integer isKeynote;
    /**
     * 标题
     */
    private String title;
}
