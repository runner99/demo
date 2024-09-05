package com.runner.testworks.controller.bjgw.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/9/22 12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsolatorsVo {
    private String nodeName;
    private List<JSONObject> childs;
}
