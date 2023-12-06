package com.runner.testworks.controller.jinhua.safe.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/12/4 9:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileParam {
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 附件地址
     */
    private String fileUrl;
}
