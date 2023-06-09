package com.runner.testworks.pojo.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/6/9 10:15
 */

@Data
public class ReqVo {
    private String cookie;
    private String url;
    private String requstMethod;
    /**
     * 请求体
     */
    private HashMap<String,Object> map;
}
