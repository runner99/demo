package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/2 22:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Integer id;
    private String timeSlot;
    private String title;
    private String info;
    private List<FileParam> opceSecureFileParams;
    private Integer isKeynote;
}
