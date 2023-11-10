package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/2 22:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadReq {
    private List idList;
    private String timeSlot;
    private Integer isKeynote;
    private String title;
}
