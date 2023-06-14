package com.runner.testworks.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author weizhenqiang
 * @date 2023/6/14 10:08
 */
@Data
public class IndicatorUpLoad {

    /**
     * 检测名称
     */
    private String name;

    /**
     * 检测版本
     */
    private String version;

    /**
     * 描述
     */
    private String desc;

    /**
     * 指标文件
     */
    private MultipartFile file;

}
