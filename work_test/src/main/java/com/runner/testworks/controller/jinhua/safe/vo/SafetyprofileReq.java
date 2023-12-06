package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/2 15:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SafetyprofileReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 时间段
     */
    private String timeSlot;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String info;

    /**
     * 文件详情
     */
    private String opceSecureFileParams;

    /**
     * 是否重点项 0否 1是
     */
    private Integer isKeynote;
}
